# Code Smells

Code smell audit of project-owned files in `quantlib_for_maven`.
Excludes `external/` (read-only submodules).

---

## Table of Contents
- Code Smells
  - Critical / Security
    - S7 — `JAVA_FINALIZER` relies on deprecated finalization
    - S8 — No SWIG `%exception` directive for C++ exceptions
    - S9 — CI workflow uses overly broad default permissions
    - S10 — Personal scoped token in CI
    - S11 — `SWIGTYPE` catch-all in `%typemap(javainterfaces)` may be ineffective
    - S12 — No SWIG `%feature("director")` for callback classes
    - S13 — Thread safety of global `Settings::instance()`
  - Performance
    - P2 — `QL_JAVA_INTERFACES` macro concatenation is fragile
    - P3 — No `%apply` / bulk typemaps for large numeric vectors
  - Modern C++ / CMake Best Practices
    - C3 — Global `CMAKE_CXX_STANDARD` instead of per-target
    - C8 — No `cmake_minimum_required` version range
    - C9 — No test or workflow presets in CMakePresets.json
    - C10 — `FORCE` on cache variables overrides user settings
    - C11 — All SWIG target includes marked `SYSTEM` — suppresses own-header warnings
    - C13 — Missing SWIG include guard
  - Modern Java / JDK 21 Best Practices
    - J10 — Version mismatch between POM and CMake
    - J12 — `DatesTest.testCanHash` — dangling native reference in HashSet

---

## Critical / Security

### S7 — `JAVA_FINALIZER` relies on deprecated finalization

**File:** `swig/QuantLibEntrypoint.i`
**Line:** 26

`target_compile_definitions(QuantLibJNI PRIVATE JAVA_FINALIZER)` enables
calling the C++ destructor from Java's `finalize()`. Finalization has been
deprecated since JDK 9 and is targeted for removal (JEP 421). On JDK 21+
finalizers may run unpredictably or not at all.

**Possible fix:** Remove the `JAVA_FINALIZER` compile definition from
`swig/CMakeLists.txt` line 26:

```cmake
# Remove this line:
# target_compile_definitions(QuantLibJNI PRIVATE JAVA_FINALIZER)
```

The project already provides `AutoCloseable` via the `SWIGTYPE` typemap
(S11) and the `QuantLibJNIHelpers.AutoCloseable` interface. Rely solely
on `try-with-resources` / explicit `close()` for deterministic cleanup.
Update the project README (already done) and Javadoc to emphasize
`try-with-resources` usage.

### S8 — No SWIG `%exception` directive for C++ exceptions

**File:** `swig/QuantLibEntrypoint.i`

There is no `%exception` block to translate C++ exceptions
(`QuantLib::Error`, `std::exception`) into Java exceptions. An uncaught C++
exception propagating through JNI will crash the JVM with no diagnostic.

**Possible fix:** Add a `%exception` block in `swig/QuantLibEntrypoint.i`
before the `%include "quantlib.i"` line:

```swig
%exception {
    try {
        $action
    } catch (QuantLib::Error& e) {
        SWIG_JavaThrowException(jenv, SWIG_JavaRuntimeException,
                                e.what());
        return $null;
    } catch (std::exception& e) {
        SWIG_JavaThrowException(jenv, SWIG_JavaRuntimeException,
                                e.what());
        return $null;
    } catch (...) {
        SWIG_JavaThrowException(jenv, SWIG_JavaRuntimeException,
                                "Unknown C++ exception");
        return $null;
    }
}
```

This ensures every JNI call is wrapped in a try/catch that translates C++
exceptions into Java `RuntimeException` instances with the original message.

### S9 — CI workflow uses overly broad default permissions

**File:** `.github/workflows/build_maven_artefact.yml`

Only the `deploy` job specifies `permissions: contents: read, packages: write`.
Other jobs inherit the repository default, which may be `write-all`.
Best practice: set `permissions: read-all` at workflow top level and grant
write only where needed.

**Possible fix:** Add a top-level `permissions` block at the workflow level:

```yaml
name: Build the QuantLib maven artefact

on: [push, pull_request]

permissions:
  contents: read

jobs:
  # …
  deploy:
    permissions:
      contents: read
      packages: write
    # …
```

This follows the principle of least privilege. Each job that needs write
access declares it explicitly.

### S10 — Personal scoped token in CI

**File:** `.github/workflows/build_with_quantlib_latest.yml`
**Line:** 30

Uses `REPO_SCOPED_TOKEN` — a personal access token. If this token has
broad scopes it is a privilege escalation vector. Prefer fine-grained
GitHub App tokens.

**Possible fix:** Replace the personal access token with a GitHub App
installation token:

1. Create a GitHub App with only the required permissions
   (e.g., `contents: write` on this repo only).
2. Install the app on the repository.
3. Use `actions/create-github-app-token@v1` in the workflow:

```yaml
- name: Generate token
  id: app-token
  uses: actions/create-github-app-token@v1
  with:
    app-id: ${{ secrets.APP_ID }}
    private-key: ${{ secrets.APP_PRIVATE_KEY }}

- name: Checkout with app token
  uses: actions/checkout@v4
  with:
    token: ${{ steps.app-token.outputs.token }}
```

This limits the token's scope and lifetime automatically.

### S11 — `SWIGTYPE` catch-all in `%typemap(javainterfaces)` may be ineffective

**File:** `swig/QuantLibEntrypoint.i`
**Line:** 16

```
%typemap(javainterfaces) SWIGTYPE "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable";
```

`SWIGTYPE` is a pseudo-type used by SWIG as a fallback. Applying
`javainterfaces` to it is an attempt to add `AutoCloseable` to every
generated proxy class, but behaviour depends on SWIG version and may not
produce correct `close()` / `delete()` semantics on each class. Per-class
typemaps or `%shared_ptr` wrappers would be more reliable.

**Possible fix:** This actually works as intended in SWIG 4.4.x as a
catch-all. To make it more robust and self-documenting:

1. Add a comment explaining the intent:
   ```swig
   // Apply AutoCloseable to ALL generated proxy classes.
   // SWIGTYPE matches any wrapped C++ type in SWIG 4.4+.
   ```
2. Add a SWIG test (`.i` file that wraps a dummy class) to verify the
   `AutoCloseable` interface is correctly applied.
3. For critical classes that need guaranteed `close()` behaviour, add
   explicit per-class typemaps as insurance:
   ```swig
   %typemap(javainterfaces) QuantLib::Date
       "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable";
   ```

### S12 — No SWIG `%feature("director")` for callback classes

**File:** `swig/QuantLibEntrypoint.i`

If Java subclasses need to override C++ virtual methods (e.g. custom cost
functions or pricing engines), SWIG directors must be enabled. No
`%feature("director")` directive is present, so any Java→C++ callback will
silently call the base class instead of the Java override.

**Possible fix:** Identify C++ classes that users may want to subclass in
Java and enable directors for them in `swig/QuantLibEntrypoint.i`:

```swig
%feature("director") CostFunction;
%feature("director") PricingEngine;
%feature("director") StochasticProcess;
```

Also pass the `-directors` flag to SWIG in `swig/CMakeLists.txt`:

```cmake
set_property(TARGET QuantLibJNI PROPERTY SWIG_COMPILE_OPTIONS
    -package org.quantlib -directors)
```

Note: directors add overhead and complexity. Only enable them for classes
where Java subclassing is a real use case. Document which classes support
Java-side subclassing.

### S13 — Thread safety of global `Settings::instance()`

**File:** conceptual — affects all QuantLib calls via SWIG

QuantLib's `Settings::instance()` holds global mutable state (evaluation
date, etc.). Concurrent Java threads calling into QuantLib can race on
this singleton. The SWIG layer provides no synchronization or
documentation of thread-safety constraints.

**Possible fix:** This is a fundamental QuantLib design constraint that
cannot be fully fixed in the SWIG layer. Mitigation options:

1. **Document it prominently** in the README and Javadoc:

   ```java
   /**
    * WARNING: QuantLib is NOT thread-safe. All calls to QuantLib
    * must be serialized. Use a single-threaded executor or explicit
    * synchronization when calling from multiple Java threads.
    */
   ```

2. **Provide a Java-side synchronization wrapper** (optional convenience):

   ```java
   public final class QuantLibLock {
       private static final ReentrantLock LOCK = new ReentrantLock();

       public static void run(Runnable task) {
           LOCK.lock();
           try { task.run(); } finally { LOCK.unlock(); }
       }

       public static <T> T call(Callable<T> task) throws Exception {
           LOCK.lock();
           try { return task.call(); } finally { LOCK.unlock(); }
       }
   }
   ```

3. **Enable `QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN`** (already set in
   `CMakeLists.txt` line 20) — but note this only protects the observer
   pattern, not `Settings::instance()` or other global state.

---

## Performance

### P2 — `QL_JAVA_INTERFACES` macro concatenation is fragile

**File:** `swig/QuantLibEntrypoint.i`
**Line:** 15

```
#define QL_JAVA_INTERFACES "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable, "
```

The trailing `, ` requires that the downstream consumer always appends
something after it. If the external SWIG template changes, this will
silently produce an invalid Java `implements` clause or a compile error.

**Possible fix:** Remove the trailing `, ` from the macro and adjust the
downstream usage to handle concatenation:

```swig
#define QL_JAVA_INTERFACES "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable"
```

Then in the `%typemap(javainterfaces)` for `std::vector` (or wherever it's
concatenated), use a comma prefix:

```swig
%typemap(javainterfaces) std::vector
    QL_JAVA_INTERFACES ", java.util.RandomAccess";
```

This way the macro is a self-contained value and the concatenation point
is explicit.

### P3 — No `%apply` / bulk typemaps for large numeric vectors

**File:** `swig/QuantLibEntrypoint.i`

`stl.i` is included for `std::vector` support, but there are no targeted
`%apply` directives to convert `std::vector<double>` / `std::vector<Real>`
to Java `double[]` efficiently. Every element crosses the JNI boundary
individually, which is a significant overhead for large time series,
volatility surfaces, and cashflow vectors.

**Possible fix:** Add bulk array typemaps using SWIG's `arrays_java.i` and
custom JNI code. Example for `std::vector<double>`:

```swig
%include "arrays_java.i"

%typemap(jtype)   std::vector<double> "double[]"
%typemap(jstype)  std::vector<double> "double[]"
%typemap(jni)     std::vector<double> "jdoubleArray"
%typemap(javain)  std::vector<double> "$javainput"
%typemap(javaout) std::vector<double> { return $jnicall; }

%typemap(in) std::vector<double> (jdouble *jarr) {
    if (!$input) { SWIG_JavaThrowException(jenv,
        SWIG_JavaNullPointerException, "null array"); return $null; }
    jsize sz = jenv->GetArrayLength($input);
    jarr = jenv->GetDoubleArrayElements($input, nullptr);
    $1.assign(jarr, jarr + sz);
    jenv->ReleaseDoubleArrayElements($input, jarr, JNI_ABORT);
}

%typemap(out) std::vector<double> {
    jsize sz = static_cast<jsize>($1.size());
    $result = jenv->NewDoubleArray(sz);
    jenv->SetDoubleArrayRegion($result, 0, sz, $1.data());
}
```

This copies the entire vector in one JNI call instead of element-by-element.

---

## Modern C++ / CMake Best Practices

### C3 — Global `CMAKE_CXX_STANDARD` instead of per-target

**File:** `CMakeLists.txt`
**Line:** 15

```cmake
set(CMAKE_CXX_STANDARD 17)
```

Modern CMake: `target_compile_features(mylib PUBLIC cxx_std_17)` per target,
which also propagates to dependents.

**Possible fix:** Replace the global variables with per-target compile
features:

```cmake
# Remove these global lines:
# set(CMAKE_CXX_STANDARD 17)
# set(CMAKE_CXX_STANDARD_REQUIRED ON)
# set(CMAKE_CXX_EXTENSIONS OFF)

# In swig/CMakeLists.txt, after add_library:
target_compile_features(QuantLibJNI PRIVATE cxx_std_17)
set_target_properties(QuantLibJNI PROPERTIES
    CXX_EXTENSIONS OFF)
```

### C8 — No `cmake_minimum_required` version range

**File:** `CMakeLists.txt`
**Line:** 1

```cmake
cmake_minimum_required(VERSION 4.0.0)
```

Version 4.0.0 is very new (2025). A version range like
`VERSION 3.28...4.0` would allow building with slightly older CMake while
still benefiting from 4.0 policies.

**Possible fix:** Use a version range:

```cmake
cmake_minimum_required(VERSION 3.28...4.0)
```

This allows CMake 3.28+ to configure the project while applying 4.0
policies when available. Check that all CMake features used are available
in 3.28 (JNI imported targets require 3.24, presets require 3.21).

### C9 — No test or workflow presets in CMakePresets.json

**File:** `CMakePresets.json`

Only configure + build presets are defined. Adding test presets and
workflow presets (configure → build → test) would streamline CI and local
development.

**Possible fix:** Add test and workflow presets to `CMakePresets.json`:

```json
{
  "testPresets": [
    {
      "name": "release",
      "configurePreset": "release",
      "output": { "outputOnFailure": true }
    }
  ],
  "workflowPresets": [
    {
      "name": "release",
      "steps": [
        { "type": "configure", "name": "release" },
        { "type": "build", "name": "release" },
        { "type": "test", "name": "release" }
      ]
    }
  ]
}
```

Then CI and developers can run: `cmake --workflow --preset release`.

### C10 — `FORCE` on cache variables overrides user settings

**File:** `CMakeLists.txt`
**Lines:** 20–22

```cmake
set(QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN ON CACHE BOOL "..." FORCE)
```

Using `FORCE` prevents users from overriding these values via `-D` flags or
the CMake GUI. Prefer `option()` or `set(… CACHE …)` without `FORCE`
unless the override is truly mandatory.

**Possible fix:** If these values are truly project requirements (not user
preferences), document why `FORCE` is needed. Otherwise, replace with
non-forced cache variables:

```cmake
# If the value MUST be ON for correctness:
set(QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN ON CACHE BOOL
    "Required for Java thread safety (see S13)" FORCE)
# Add a comment explaining why FORCE is necessary

# If the value is a default that users should be able to override:
option(QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN
    "Enable thread-safe observer pattern" ON)
```

### C11 — All SWIG target includes marked `SYSTEM` — suppresses own-header warnings

**File:** `swig/CMakeLists.txt`
**Line:** 59

```cmake
target_include_directories(QuantLibJNI SYSTEM PRIVATE
    ${QL_MVN_QUANTLIB_GENERATED_HEADERS_DIR} ${QL_MVN_QUANTLIB_ROOT_DIR}
    ${Boost_INCLUDE_DIRS} ${JNI_INCLUDE_DIRS})
```

Marking project-owned generated headers as `SYSTEM` suppresses compiler
warnings in those headers. Only third-party includes (`Boost`, `JNI`)
should be `SYSTEM`; project headers should remain non-`SYSTEM` so that
warnings are visible.

**Possible fix:** Split into `SYSTEM` and non-`SYSTEM` include directories:

```cmake
# Project headers (warnings enabled):
target_include_directories(QuantLibJNI PRIVATE
    ${QL_MVN_QUANTLIB_GENERATED_HEADERS_DIR}
    ${QL_MVN_QUANTLIB_ROOT_DIR})

# Third-party headers (warnings suppressed):
target_include_directories(QuantLibJNI SYSTEM PRIVATE
    ${Boost_INCLUDE_DIRS}
    ${JNI_INCLUDE_DIRS})
```

Or, combined with C2 and C5, use imported targets which handle `SYSTEM`
automatically:

```cmake
target_include_directories(QuantLibJNI PRIVATE
    ${QL_MVN_QUANTLIB_GENERATED_HEADERS_DIR}
    ${QL_MVN_QUANTLIB_ROOT_DIR})
target_link_libraries(QuantLibJNI PRIVATE
    Boost::headers JNI::JNI)
```

---

## Modern Java / JDK 21 Best Practices

### J10 — Version mismatch between POM and CMake

**File:** `java/pom.xml` (`0.1.0-SNAPSHOT`) vs `CMakeLists.txt` (`project(VERSION 1.41.0)`)

The two version numbers are unrelated, which will confuse consumers.

**Possible fix:** Align the versions. Option A — derive the Maven version
from CMake:

```cmake
# In CMakeLists.txt, after project():
set(QL_MVN_VERSION
    "${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}.${PROJECT_VERSION_PATCH}")
configure_file(java/pom.xml.in java/pom.xml @ONLY)
```

Option B — use a Maven property set during the CMake build:

```cmake
add_custom_command(TARGET QuantLibJNI POST_BUILD
    COMMAND ${MVN_EXECUTABLE} versions:set
        -DnewVersion=${PROJECT_VERSION}
    WORKING_DIRECTORY ${CMAKE_SOURCE_DIR}/java)
```

Option C (simplest) — manually keep them in sync and add a CI check:

```yaml
# In CI workflow:
- name: Check version alignment
  run: |
    cmake_ver=$(grep 'project.*VERSION' CMakeLists.txt \
                | grep -oP '\d+\.\d+\.\d+')
    mvn_ver=$(./mvnw help:evaluate \
              -Dexpression=project.version -q -DforceStdout \
              | sed 's/-SNAPSHOT//')
    [ "$cmake_ver" = "$mvn_ver" ] || \
      (echo "Version mismatch: CMake=$cmake_ver Maven=$mvn_ver"; exit 1)
```

### J12 — `DatesTest.testCanHash` — dangling native reference in HashSet

**File:** `java/src/test/java/org/quantlib/DatesTest.java`
**Lines:** 326–327

A `Date` SWIG object is added to a `HashSet`, then `startDate.close()` is
called. The `HashSet` still holds a reference to the now-deleted native
object. Any subsequent operation on the set entry (e.g. iteration, contains
check) will access freed memory.

**Possible fix:** Remove the `Date` from the `HashSet` before closing it,
or restructure the test to close after all set operations:

```java
@Test
void testCanHash() {
    var set = new HashSet<Date>();
    try (var startDate = new Date(1, Month.January, 2020);
         var endDate = new Date(31, Month.December, 2020)) {
        set.add(startDate);
        set.add(endDate);

        assertTrue(set.contains(startDate));
        assertEquals(2, set.size());

        // Remove from set BEFORE close() is called by try-with-resources
        set.remove(startDate);
        set.remove(endDate);
    }
    // Now the set is empty and the dates are closed — no dangling refs
}
```
