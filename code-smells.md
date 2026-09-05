# Code Smells

Code smell audit of project-owned files in `quantlib_for_maven`.
Excludes generated files and treats `external/` as read-only upstream code.

This audit intentionally separates real project risks from intentional design
choices. The project is a thin Maven packaging layer around upstream QuantLib
and QuantLib-SWIG, so items that would require adding new QuantLib-SWIG
functionality are not listed unless they affect this repository's packaging,
build, or documented behavior.

---

## Table of Contents
- Code Smells
  - Security / CI
    - S9 - CI workflow uses overly broad default permissions
    - S10 - Repository-scoped token use is not scope-documented
  - SWIG / JNI Maintainability
    - S11 - AutoCloseable catch-all lacks a local regression guard
    - S13 - QuantLib global state thread-safety is under-documented
    - P2 - `QL_JAVA_INTERFACES` macro concatenation is fragile
  - CMake Maintainability
    - C3 - Global C++ standard setting instead of target-scoped features

---

## Security / CI

### S9 - CI workflow uses overly broad default permissions

**Status:** addressed.

**Files:**

- `.github/workflows/build_maven_artefact.yml`
- `.github/workflows/build_native_libraries.yml`
- `.github/workflows/build_with_quantlib_latest.yml`

The repository default is a read-write `GITHUB_TOKEN`
(`default_workflow_permissions: "write"`), so any job without a `permissions`
block was handed `contents: write` and `actions: write` while only compiling and
running third-party actions and Maven plugins.

Each workflow now declares least privilege:

- `build_native_libraries.yml` and `build_with_quantlib_latest.yml` set
  `permissions: contents: read` at workflow level.
- `build_maven_artefact.yml` sets `contents: read` on `build-for-deploy` and on
  `deploy-quantlib-snapshot`. The latter's `packages: write` was dropped: the
  publish path is `central-publishing-maven-plugin` against Maven Central, and
  `java/pom.xml` declares no `<distributionManagement>`, so nothing targeted
  GitHub Packages.

### S10 - Repository-scoped token use is not scope-documented

**Files:**

- `.github/workflows/build_with_quantlib_latest.yml`
- `.github/workflows/stale.yml`

Both workflows use `secrets.REPO_SCOPED_TOKEN`. This may be a fine-grained token
with appropriate permissions, but the workflow does not document the required
minimal scopes or why `GITHUB_TOKEN` is insufficient. That makes privilege review
harder and can lead to over-scoped long-lived credentials.

**Possible fix:** Prefer a GitHub App installation token for automation that
needs cross-workflow or branch-update permissions. If a personal or fine-grained
token remains necessary, document its exact required permissions next to the
workflow use, for example:

```yaml
# Required token permissions:
# - contents: write, for creating update branches
# - pull-requests: write, for opening update PRs
# Prefer a fine-grained token or GitHub App installation token.
token: ${{ secrets.REPO_SCOPED_TOKEN }}
```

---

## SWIG / JNI Maintainability

### S11 - AutoCloseable catch-all lacks a local regression guard

**File:** `swig/QuantLibEntrypoint.i`
**Line:** 19

```swig
%typemap(javainterfaces) SWIGTYPE "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable";
```

The `SWIGTYPE` catch-all is intentional and matches the repository guidance that
all generated QuantLib proxy classes implement `AutoCloseable`. Upstream
QuantLib-SWIG uses the same broad `SWIGTYPE` pattern for Java autoclose support.

The maintainability risk is not that the pattern is wrong; it is that this
project depends on broad SWIG typemap behavior for native-memory safety without
a small local regression check or explanatory comment in the entrypoint.

**Possible fix:** Add a short comment and a focused test that verifies a generated
proxy class implements `AutoCloseable` and that `close()` delegates to `delete()`:

```swig
// Apply AutoCloseable to all generated proxy classes. SWIGTYPE is the generic
// fallback typemap used by SWIG and upstream QuantLib-SWIG for this purpose.
%typemap(javainterfaces) SWIGTYPE "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable";
```

### S13 - QuantLib global state thread-safety is under-documented

**File:** conceptual, affects QuantLib calls through SWIG

QuantLib's `Settings::instance()` holds global mutable state such as the
evaluation date. Concurrent Java threads calling into QuantLib can observe or
mutate that global state unexpectedly. `QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN`
is enabled for observer-pattern behavior, but it does not make all QuantLib
global state or all pricing calls thread-safe.

This is not a local implementation bug in the SWIG entrypoint. It is a usage
hazard that should be documented clearly because Java users may assume library
calls are safe to run concurrently.

**Possible fix:** Document the constraint prominently in the README and Javadocs:

```java
/**
 * WARNING: QuantLib contains process-wide mutable state such as Settings.
 * Applications that use Settings.instance() from multiple Java threads must
 * serialize access or isolate calculations to avoid cross-thread interference.
 */
```

Optionally provide a small Java-side synchronization helper for users that want
to serialize calls explicitly.

### P2 - `QL_JAVA_INTERFACES` macro concatenation is fragile

**File:** `swig/QuantLibEntrypoint.i`
**Line:** 18

```swig
#define QL_JAVA_INTERFACES "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable, "
```

The trailing comma is intentional because the macro is concatenated with
`java.util.RandomAccess` for `std::vector` wrappers. However, the macro is not a
self-contained interface list. Any future use that does not append another
interface would generate an invalid Java `implements` clause.

**Possible fix:** Make the macro self-contained and move comma placement to the
call site:

```swig
#define QL_JAVA_INTERFACES "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable"

%extend std::vector {
    %typemap(javainterfaces) std::vector QL_JAVA_INTERFACES ", java.util.RandomAccess";
};
```

If keeping the current pattern for consistency with upstream QuantLib-SWIG,
add a comment explaining that the macro intentionally includes a trailing comma
and must only be used before another interface name.

---

## CMake Maintainability

### C3 - Global C++ standard setting instead of target-scoped features

**File:** `CMakeLists.txt`
**Lines:** 49-51

```cmake
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 17)
```

Modern CMake generally prefers target-scoped features such as
`target_compile_features(QuantLibJNI PRIVATE cxx_std_17)`. In this repository,
the global setting is understandable because upstream QuantLib is added as a
subdirectory and also needs the same standard. The maintainability issue is that
global settings are easier to leak to unrelated future targets.

**Possible fix:** Keep the global setting only if it is required by the upstream
QuantLib subdirectory. For project-owned targets, also document or enforce the
requirement locally:

```cmake
target_compile_features(QuantLibJNI PRIVATE cxx_std_17)
set_target_properties(QuantLibJNI PROPERTIES CXX_EXTENSIONS OFF)
```

---

## Removed After Re-evaluation

The following previous entries were removed because I do not agree they are
current project code smells:

- **S8 - No SWIG `%exception` directive for C++ exceptions:** upstream
  `external/QuantLib-SWIG/SWIG/quantlib.i`, which `QuantLibEntrypoint.i`
  includes, already includes `exception.i` and defines a global `%exception`.
- **S12 - No SWIG `%feature("director")` for callback classes:** upstream
  QuantLib-SWIG enables Java directors at module level and provides delegate
  wrappers such as `CostFunctionDelegate`. General Java subclassing is not a
  stated goal for this Maven packaging repository.
- **P3 - No `%apply` / bulk typemaps for large numeric vectors:** this is a
  speculative optimization and could change generated Java APIs. No local
  bottleneck or requirement justifies tracking it as a smell.
- **C8 - No `cmake_minimum_required` version range:** the repository explicitly
  requires CMake 4.0.0+ in its documented prerequisites and presets.
- **C9 - No test or workflow presets in CMakePresets.json:** tests are Maven
  based and there is no project-owned CTest wiring. CMake test presets would not
  add much until such wiring exists.
- **C10 - `FORCE` on cache variable overrides user settings:** the forced
  `QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN` option is documented as required for
  this build's QuantLib/SWIG behavior.
- **C11 - Project-owned SWIG target includes marked `SYSTEM`:** the marked paths
  are generated QuantLib headers and the read-only upstream QuantLib source tree,
  not ordinary project-owned headers. Suppressing upstream/generated warnings is
  reasonable with warnings-as-errors enabled.
- **J10 - Version mismatch between POM and CMake:** the checked-in POM keeps a
  snapshot placeholder and the release workflow sets the Maven artifact version
  before deployment. The split is intentional, though release/versioning docs
  should remain clear.
- **J12 - `DatesTest.testCanHash` dangling native reference:** the `HashSet` is
  local and is used only before the try-with-resources block closes `startDate`.
  There is no post-close access or escaping reference in the current test.
