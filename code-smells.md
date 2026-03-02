# Code Smells

Code smell audit of project-owned files in `quantlib_for_maven`.
Excludes `external/` (read-only submodules).

---

## Critical / Security

### ~~S1 — Predictable temp directory in native library loading~~

**File:** `java/src/main/java/cz/adamh/utils/NativeUtils.java`
**Lines:** 82–92

`createTempDirectory()` constructs a directory name from `"nativeutils"` +
`System.nanoTime()`, then calls `File.mkdir()`. `nanoTime()` is predictable;
an attacker who can write to the system temp directory can win a race and
substitute a malicious shared library before the real one is copied in
(CWE-377 insecure temporary file, CWE-427 uncontrolled search path).

**Modern alternative:** `java.nio.file.Files.createTempDirectory()` which
creates the directory with restrictive permissions atomically.

**Possible fix:** Replace the entire `createTempDirectory()` method body:

```java
private static Path createTempDirectory(String prefix) throws IOException {
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
        return Files.createTempDirectory(prefix);
    }
    var permissions = PosixFilePermissions.asFileAttribute(
            EnumSet.of(PosixFilePermission.OWNER_READ,
                       PosixFilePermission.OWNER_WRITE,
                       PosixFilePermission.OWNER_EXECUTE));
    return Files.createTempDirectory(prefix, permissions);
}
```

This uses `java.nio.file.Files.createTempDirectory()` which atomically
creates the directory with a cryptographically random suffix. On POSIX
systems, the explicit `PosixFilePermissions` restrict access to the owner
only (mode `0700`).

### ~~S2 — REPLACE_EXISTING copy into guessable temp path~~

**File:** `java/src/main/java/cz/adamh/utils/NativeUtils.java`
**Line:** 127

`Files.copy(…, StandardCopyOption.REPLACE_EXISTING)` into the directory
created in S1. If an attacker pre-creates the file, this silently replaces
their payload — or if they win the race after copy, they overwrite the
legitimate library.

**Possible fix:** After fixing S1 (secure temp directory), change the copy
to fail if the target already exists instead of replacing it:

```java
Files.copy(is, temp, StandardCopyOption.COPY_ATTRIBUTES);
// Do NOT use REPLACE_EXISTING — if the file already exists,
// something is wrong and we should fail loudly.
```

Alternatively, use `CREATE_NEW` semantics by opening a `FileChannel` with
`StandardOpenOption.CREATE_NEW` and copying bytes manually. Combined with
the owner-only directory from S1, this eliminates the race window.

### ~~S3 — `deleteOnExit()` is unreliable for cleanup~~

**File:** `java/src/main/java/cz/adamh/utils/NativeUtils.java`
**Lines:** 105, 128

`deleteOnExit()` does not run on JVM crash, `kill -9`, or `System.halt()`.
Leftover native libraries in world-readable temp directories widen the attack
surface.

**Possible fix:** Replace `deleteOnExit()` with a shutdown-hook that uses
`Files.deleteIfExists()`, and accept that abrupt termination will leave
files behind (which is unavoidable). Additionally, on startup, scan for
and clean up stale `nativeutils*` directories from previous runs:

```java
// In loadLibraryFromJar(), after successful System.load():
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    try {
        Files.deleteIfExists(temp);
        Files.deleteIfExists(temp.getParent());
    } catch (IOException ignored) {
        // Best-effort cleanup
    }
}));
```

For a more robust approach, write a lock file into the temp directory and
have each startup attempt to clean up directories without active locks.

### ~~S4 — Legacy `java.io.File` API used throughout NativeUtils~~

**File:** `java/src/main/java/cz/adamh/utils/NativeUtils.java`

The entire class uses `java.io.File` instead of `java.nio.file.Path` /
`java.nio.file.Files`. The old API does not report permission failures,
cannot set POSIX permissions, and `mkdir()` silently returns `false` on
failure (line 94 does check, but the pattern is fragile).

**Possible fix:** Rewrite the class to use `java.nio.file.Path` throughout.
Key replacements:

| Old (`java.io.File`)                | New (`java.nio.file`)                           |
| ----------------------------------- | ----------------------------------------------- |
| `new File(System.getProperty("…"))` | `Path.of(System.getProperty("…"))`              |
| `file.mkdir()`                      | `Files.createDirectory(path)`                   |
| `file.exists()`                     | `Files.exists(path)`                            |
| `file.deleteOnExit()`               | Shutdown hook with `Files.deleteIfExists(path)` |
| `new FileOutputStream(file)`        | `Files.newOutputStream(path)`                   |
| `file.getAbsolutePath()`            | `path.toAbsolutePath().toString()`              |

This also fixes S1–S3 as side effects since the new API provides atomic
creation with permissions and better error reporting.

### ~~S5 — Non-volatile, unsynchronized flag for library loading~~

**File:** `java/src/main/java/org/quantlib/helpers/QuantLibJNIHelpers.java`
**Line:** 18

`private static boolean libraryIsLoaded` is neither `volatile` nor guarded
by a lock. In a multi-threaded startup scenario the library can be loaded
multiple times or partially observed as loaded.

**Possible fix:** Use double-checked locking with `volatile`:

```java
private static volatile boolean libraryIsLoaded = false;

static void loadLibrary() {
    if (libraryIsLoaded) return;
    synchronized (QuantLibJNIHelpers.class) {
        if (libraryIsLoaded) return;
        // … existing loading logic …
        libraryIsLoaded = true;
    }
}
```

Alternatively, use a holder-class idiom for lazy thread-safe initialization:

```java
private static final class LibraryHolder {
    static {
        // loading logic here — guaranteed once by JVM class loading
        loadLibraryInternal();
    }
}

static void loadLibrary() {
    // Accessing the class triggers the static initializer exactly once
    var ignored = LibraryHolder.class;
}
```

### ~~S6 — Wildcard import~~

**File:** `java/src/main/java/cz/adamh/utils/NativeUtils.java`
**Line:** 26

`import java.io.*` — violates the project style guide (no wildcard imports).

**Possible fix:** Replace with explicit imports:

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
```

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

### ~~P1 — Native resource leaks in tests (missing try-with-resources)~~

**Files:**

- `java/src/test/java/org/quantlib/ZigguratXoshiro256StarStarGaussianTest.java` — `rng`, `gaussianRng`, `gaussianRsg`, `sample` never closed
- `java/src/test/java/org/quantlib/QuantLibDateToLocalDateTest.java:16` — `Date.of(localDate)` inside `assertEquals`, never closed
- `java/src/test/java/org/quantlib/InstrumentTest.java` — `flatRate()` / `flatVol()` helpers create `SimpleQuote`, `QuoteHandle`, `NullCalendar`, `FlatForward` etc. that are never closed
- `java/src/test/java/org/quantlib/EuropeanOptionTest.java` — same helpers; `testValues` / `testGreeks` create `dayCounter`, `today`, `spot`, `qRate`, `rRate`, `vol`, `qTS`, `rTS`, `volTS` outside try-with-resources; `testImpliedVol` creates `exDate` before the try block
- `java/src/test/java/org/quantlib/EquityIndexTest.java` — multiple SWIG objects (`calendar`, `eqIdx`, `fixingDate`, etc.) created without try-with-resources
- `java/src/test/java/org/quantlib/DatesTest.java` — `testCanHash` puts `Date` objects into `HashSet`; after `startDate.close()` the set holds a dangling native reference

Each leaked SWIG object retains a C++ heap allocation until the GC
finalizer runs (if ever, see S7). In large test suites this can exhaust
native memory.

**Possible fix:** Wrap every SWIG object in `try-with-resources`. Example
for `ZigguratXoshiro256StarStarGaussianTest`:

```java
@Test
void testZiggurat() {
    try (var rng = new Xoshiro256StarStarUniformRng(42);
         var gaussianRng = new ZigguratGaussianRng(rng)) {
        for (int i = 0; i < 1000; i++) {
            try (var sample = gaussianRng.next()) {
                var value = sample.value();
                assertThat(value).isBetween(-10.0, 10.0);
            }
        }
    }
}
```

For `DatesTest.testCanHash`, close the `Date` after removing it from the
`HashSet`, or restructure to not hold SWIG objects in collections past their
lifetime:

```java
try (var startDate = new Date(1, Month.January, 2020)) {
    set.add(startDate);
    assertTrue(set.contains(startDate));
    set.remove(startDate);
}   // close AFTER removing from the set
```

For test helper methods like `flatRate()` and `flatVol()`, consider
returning a composite `AutoCloseable` that closes all intermediate objects,
or restructure the helpers to accept pre-created objects.

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

### C1 — Directory-scoped `add_compile_options` instead of target-scoped

**File:** `CMakeLists.txt`
**Line:** 60

```cmake
add_compile_options(-isystem ${Boost_INCLUDE_DIRS})
```

Affects all targets in the directory. Modern CMake: use
`target_compile_options` / `target_include_directories(… SYSTEM …)` on the
specific target.

**Possible fix:** Remove the `add_compile_options` line and add Boost as a
`SYSTEM` include on each target that needs it:

```cmake
# In swig/CMakeLists.txt (the only consumer):
target_include_directories(QuantLibJNI SYSTEM PRIVATE
    ${Boost_INCLUDE_DIRS})
```

Or better yet, use the imported target (see C2):

```cmake
target_link_libraries(QuantLibJNI PRIVATE Boost::headers)
```

### C2 — Old-style `${Boost_INCLUDE_DIRS}` instead of imported target

**Files:** `CMakeLists.txt:60`, `swig/CMakeLists.txt:50`

Modern CMake provides `Boost::headers` (header-only) and component targets.
Using variables instead of imported targets loses transitive property
propagation.

**Possible fix:** Replace all `${Boost_INCLUDE_DIRS}` usage with the
imported target:

```cmake
# In swig/CMakeLists.txt:
target_link_libraries(QuantLibJNI PRIVATE
    ql_library
    Boost::headers)

# Remove from target_include_directories:
# ${Boost_INCLUDE_DIRS}   ← delete this

# In CMakeLists.txt, remove:
# add_compile_options(-isystem ${Boost_INCLUDE_DIRS})   ← delete this
```

`Boost::headers` automatically sets up include paths with `SYSTEM` and
propagates all necessary properties.

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

### C4 — Global `BUILD_SHARED_LIBS OFF`

**File:** `CMakeLists.txt`
**Line:** 19

This is a global variable that affects all `add_library()` calls, including
any added via `FetchContent` or subdirectories. Prefer setting the library
type explicitly on the target.

**Possible fix:** Remove the global setting and set the library type
explicitly on each target:

```cmake
# Remove:
# set(BUILD_SHARED_LIBS OFF)

# QuantLib is already built via add_subdirectory with its own
# QL_BUILD_SHARED_LIBS option.  For QuantLibJNI (which must be
# shared for JNI), it's already:
add_library(QuantLibJNI SHARED ...)
```

If QuantLib itself needs to be static, pass it as a subdirectory option:

```cmake
set(QL_BUILD_SHARED_LIBS OFF CACHE BOOL "" FORCE)
add_subdirectory(external/QuantLib)
```

### C5 — `${JNI_INCLUDE_DIRS}` variable instead of `JNI::JNI` target

**File:** `swig/CMakeLists.txt`
**Line:** 50

```cmake
target_include_directories(QuantLibJNI PRIVATE ${JNI_INCLUDE_DIRS})
```

Since CMake 3.24, `FindJNI` provides imported targets (`JNI::JNI`,
`JNI::JVM`). With `cmake_minimum_required(VERSION 4.0.0)` these are
available and preferred.

**Possible fix:** Replace the variable with the imported target:

```cmake
# Remove:
# target_include_directories(QuantLibJNI PRIVATE ${JNI_INCLUDE_DIRS})

# Add:
target_link_libraries(QuantLibJNI PRIVATE JNI::JNI)
```

`JNI::JNI` automatically provides the correct include directories and
handles platform-specific linking.

### C6 — POST_BUILD step runs Maven from CMake

**File:** `swig/CMakeLists.txt`
**Lines:** 69–74

```cmake
add_custom_command(TARGET QuantLibJNI POST_BUILD
    COMMAND ./mvnw clean verify …)
```

Couples the C++ build to a Maven installation. Uses relative path `./mvnw`
which depends on working directory. If Maven is unavailable, the CMake
build fails with a confusing error. Consider making this an optional
target or a separate step.

**Possible fix:** Replace the `POST_BUILD` custom command with an optional
custom target:

```cmake
option(QL_MVN_BUILD_JAVA "Build Java artifacts after native build" ON)

if(QL_MVN_BUILD_JAVA)
    find_program(MVN_EXECUTABLE mvnw PATHS ${CMAKE_SOURCE_DIR}/java
                 NO_DEFAULT_PATH)
    if(MVN_EXECUTABLE)
        add_custom_target(java_build
            COMMAND ${MVN_EXECUTABLE} clean verify
                -Dmaven.javadoc.skip=true
                -Dswig.skip=true
            WORKING_DIRECTORY ${CMAKE_SOURCE_DIR}/java
            DEPENDS QuantLibJNI
            COMMENT "Building Java artifacts with Maven"
        )
    else()
        message(WARNING "Maven wrapper not found; "
                "skipping Java build target")
    endif()
endif()
```

This makes the Maven step opt-in (`cmake -DQL_MVN_BUILD_JAVA=OFF`), uses
absolute paths, and provides a clear warning if Maven is unavailable.

### C7 — Generic variable name `ARCH` in global scope

**File:** `cmake/CreateJavaLibraryDirectory.cmake`

`set(ARCH …)` without a project prefix risks collision with other CMake
modules that also define `ARCH`.

**Possible fix:** Prefix the variable with the project namespace:

```cmake
# Replace:
# set(ARCH ...)
# With:
set(QL_MVN_ARCH ...)

# And update all references:
# ${ARCH} → ${QL_MVN_ARCH}
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

### ~~C12 — Source-tree mutation during configure~~

**File:** `cmake/CreateJavaLibraryDirectory.cmake`
**Lines:** 31–32

`file(MAKE_DIRECTORY …)` creates directories under `java/target/` in the
source tree during CMake configure. Generated artifacts and directories
are better placed under `${CMAKE_BINARY_DIR}` to keep the source tree
clean.

**Possible fix:** Create the directories under `${CMAKE_BINARY_DIR}` and
pass the paths to Maven:

```cmake
set(QL_MVN_JAVA_NATIVE_DIR
    "${CMAKE_BINARY_DIR}/java-native/${QL_MVN_ARCH}")
file(MAKE_DIRECTORY ${QL_MVN_JAVA_NATIVE_DIR})

# Pass to the SWIG target so it knows where to output:
set_target_properties(QuantLibJNI PROPERTIES
    LIBRARY_OUTPUT_DIRECTORY ${QL_MVN_JAVA_NATIVE_DIR}
    RUNTIME_OUTPUT_DIRECTORY ${QL_MVN_JAVA_NATIVE_DIR})
```

This keeps the source tree clean and makes out-of-source builds truly
out-of-source. The Maven build would then need a property to locate the
native libraries from the build directory.

### C13 — Missing SWIG include guard

**File:** `swig/QuantLibEntrypoint.i`

No `%ifndef` / `%define` guard around the file contents. Repeated
inclusion in complex SWIG include trees could produce duplicate directives
or macro redefinition warnings.

**Possible fix:** Add an include guard at the top of the file:

```swig
%ifndef QL_MVN_QUANTLIB_ENTRYPOINT_I
%define QL_MVN_QUANTLIB_ENTRYPOINT_I

// … existing file contents …

%endif // QL_MVN_QUANTLIB_ENTRYPOINT_I
```

---

## Modern Java / JDK 21 Best Practices

### J1 — Old-style `switch` statements instead of switch expressions

**File:** `java/src/main/java/org/quantlib/helpers/QuantLibJNIHelpers.java`
**Lines:** 88–100, 106–118

Java 14+ switch expressions with arrow syntax are more concise and prevent
fall-through bugs.

**Possible fix:** Replace the `switch` statements with switch expressions:

```java
// getPrefix() — lines 88–100
private static String getPrefix(OperatingSystem os) {
    return switch (os) {
        case Linux, MacOs -> "lib";
        case Windows -> "";
    };
}

// getExtension() — lines 106–118
private static String getExtension(OperatingSystem os) {
    return switch (os) {
        case Linux  -> ".so";
        case MacOs  -> ".dylib";
        case Windows -> ".dll";
    };
}
```

The arrow syntax eliminates fall-through bugs and the compiler verifies
exhaustiveness.

### J2 — `final static` modifier ordering

**File:** `java/src/main/java/org/quantlib/helpers/QuantLibJNIHelpers.java`
**Lines:** 14–17

Java convention and JLS recommendation: `static final`, not `final static`.

**Possible fix:** Swap the modifier order:

```java
// Before:
final static String LIBRARY_NAME = "QuantLibJNI";
// After:
static final String LIBRARY_NAME = "QuantLibJNI";
```

Apply to all four fields on lines 14–17.

### J3 — Enum constants not `UPPER_SNAKE_CASE`

**File:** `java/src/main/java/org/quantlib/helpers/QuantLibJNIHelpers.java`

`OperatingSystem.Linux`, `MacOs`, `Windows` — Java convention is
`LINUX`, `MAC_OS`, `WINDOWS`.

**Possible fix:** Rename the enum constants:

```java
enum OperatingSystem {
    LINUX, MAC_OS, WINDOWS
}
```

Update all references in `getPrefix()`, `getExtension()`,
`getOperatingSystem()`, and switch expressions accordingly.

### J4 — Duplicated logic in `getLibraryPath()`

**File:** `java/src/main/java/org/quantlib/helpers/QuantLibJNIHelpers.java`

Both `getPrefix()` and `getExtension()` switch on OS. A single method or
a record per OS with `(prefix, extension)` would be DRYer.

**Possible fix:** Use a record to encapsulate OS-specific library naming:

```java
private record OsLibraryConfig(String prefix, String extension) {
    static OsLibraryConfig of(OperatingSystem os) {
        return switch (os) {
            case LINUX   -> new OsLibraryConfig("lib", ".so");
            case MAC_OS  -> new OsLibraryConfig("lib", ".dylib");
            case WINDOWS -> new OsLibraryConfig("",    ".dll");
        };
    }

    String libraryFileName(String name) {
        return prefix + name + extension;
    }
}
```

Then `getLibraryPath()` simplifies to:

```java
private static String getLibraryPath() {
    var config = OsLibraryConfig.of(getOperatingSystem());
    return NATIVE_DIRECTORY + "/"
         + config.libraryFileName(LIBRARY_NAME);
}
```

### J5 — `module-info.java` does not export `org.quantlib.helpers` or `cz.adamh.utils`

**File:** `java/src/main/java/module-info.java`

Only `org.quantlib` is exported. If downstream code needs
`QuantLibJNIHelpers.AutoCloseable` (which SWIG-generated code references),
it's not accessible.

**Possible fix:** Export the `org.quantlib.helpers` package (and
optionally `cz.adamh.utils` as a qualified export):

```java
module org.quantlib {
    exports org.quantlib;
    exports org.quantlib.helpers;
    // cz.adamh.utils is an implementation detail — don't export
}
```

If `QuantLibJNIHelpers.AutoCloseable` is referenced in generated proxy
classes (which are in `org.quantlib`), it must be accessible. Since the
interface is in the same module, a same-module access works without
`exports`, but explicit export makes the API contract clear for downstream
consumers.

### J6 — Javadoc links to JDK 11 instead of JDK 17+

**File:** `java/pom.xml`
**Line:** 228

```xml
<link>https://docs.oracle.com/en/java/javase/11/docs/api/</link>
```

Project targets JDK 17+.

**Possible fix:** Update the Javadoc link to JDK 17:

```xml
<link>https://docs.oracle.com/en/java/javase/17/docs/api/</link>
```

### J7 — `<source>` / `<target>` instead of `<release>` in compiler plugin

**File:** `java/pom.xml`

`<release>17</release>` (JDK 9+) is the modern replacement — it also
validates against the correct API surface.

**Possible fix:** Replace `<source>` and `<target>` with `<release>` in the
`maven-compiler-plugin` configuration:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <configuration>
    <release>17</release>
    <!-- Remove <source> and <target> -->
  </configuration>
</plugin>
```

### J8 — Maven enforcer `requireJavaVersion` range `[17,18)` rejects JDK 21+

**File:** `java/pom.xml`

The default enforcer range only accepts JDK 17. JDK 21 and 25 support
depends on profile activation by exact JDK match. If no profile matches,
the build is rejected.

**Possible fix:** Widen the enforcer version range to accept all supported
JDKs:

```xml
<requireJavaVersion>
  <version>[17,)</version>
  <message>JDK 17 or later is required.</message>
</requireJavaVersion>
```

The `[17,)` range accepts JDK 17, 21, 25, and any future version. The
JDK-specific profiles can still activate for version-specific settings
without rejecting other JDKs.

### J9 — Javadoc `maxmemory` missing unit suffix

**File:** `java/pom.xml`
**Line:** 220

`<maxmemory>512</maxmemory>` — should be `512m`. Behaviour without the
suffix is undefined.

**Possible fix:** Add the unit suffix:

```xml
<maxmemory>512m</maxmemory>
```

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

### J11 — Reflection in tests to access private methods

**File:** `java/src/test/java/org/quantlib/helpers/QuantLibJNIHelpersTest.java`

Uses `setAccessible(true)` to test private helper methods. This is
fragile with the module system (will fail if module boundaries are
enforced), and is generally a test smell — consider package-private
visibility or testing via public API.

**Possible fix:** Change the methods under test from `private` to
package-private (default visibility):

```java
// In QuantLibJNIHelpers.java, change:
// private static String getPrefix(OperatingSystem os)
// to:
static String getPrefix(OperatingSystem os)
```

Then in the test, call directly without reflection:

```java
@Test
void testGetPrefix() {
    assertEquals("lib",
        QuantLibJNIHelpers.getPrefix(OperatingSystem.LINUX));
}
```

This requires the test class to be in the same package
(`org.quantlib.helpers`), which it already is.

Alternatively, test indirectly via the public `loadLibrary()` API if the
methods are truly implementation details that shouldn't be tested directly.

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

---

## CI/CD

### ~~CI1 — Deploy job only builds for JDK 17~~

**File:** `.github/workflows/build_maven_artefact.yml`

The `build-for-deploy` job only runs the `jdk17` matrix entry.
JDK 21/25 artifacts are not deployed.

**Possible fix:** Expand the deploy matrix to include all supported JDKs:

```yaml
build-for-deploy:
  strategy:
    matrix:
      java-version: ["17", "21", "25"]
  steps:
    # … build and deploy for each JDK version
```

If the artifact is JDK-version-independent (compiled with `--release 17`),
a single deploy is correct but should be validated against all JDKs. Add
a separate test job that runs the test suite on JDK 21 and 25 before
deploying.

### ~~CI2 — `ubuntu-22.04` runner for deploy~~

**File:** `.github/workflows/build_maven_artefact.yml`
**Line:** 53

Ubuntu 22.04 reaches end of standard support in April 2027. Consider
`ubuntu-24.04` for longer support.

**Possible fix:** Update the runner:

```yaml
# Before:
runs-on: ubuntu-22.04
# After:
runs-on: ubuntu-24.04
```

Test that the build works on 24.04 first (Boost, SWIG, and CMake package
availability).

### ~~CI3 — Submodule checkout uses `master` branch without pinning~~

**File:** `.github/workflows/build_with_quantlib_latest.yml`

`git checkout master && git pull` fetches whatever is currently on master.
A breaking change upstream will break this project's CI without any change
on this side. Consider pinning to a tag or SHA.

**Possible fix:** Pin the submodule to a specific tag or commit SHA:

```yaml
- name: Update QuantLib submodule
  run: |
    cd external/QuantLib
    git fetch origin
    git checkout v1.41.0  # Pin to known-good release tag
```

For the "build with latest" workflow (which intentionally tests against
`master`), keep the current behaviour but add failure tolerance:

```yaml
- name: Update QuantLib submodule to latest
  run: |
    cd external/QuantLib
    git checkout master && git pull
  continue-on-error: false

# Add a notification step on failure:
- name: Notify on upstream breakage
  if: failure()
  uses: actions/github-script@v7
  with:
    script: |
      github.rest.issues.create({
        owner: context.repo.owner,
        repo: context.repo.repo,
        title: 'CI: QuantLib master build broken',
        body: 'The build-with-quantlib-latest workflow failed.',
        labels: ['upstream-breakage']
      })
```

This way intentional "latest" builds still test master but create
a trackable issue when they break.
