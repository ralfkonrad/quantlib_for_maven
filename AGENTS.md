# AGENTS.md - AI Coding Agent Instructions

QuantLib Java bindings built with SWIG and CMake, published as a Maven artifact.
This file is the canonical reference for agentic coding tools working in this repository.

## Project Overview

- Repository: `quantlib_for_maven`
- Purpose: Java bindings for the QuantLib C++ library
- Maven coordinates: `io.github.ralfkonrad.quantlib_for_maven:quantlib`
- Languages: C++17 (native), Java 17+ (bindings/tests)
- Test framework: JUnit 5 (Jupiter)
- Java module: `io.github.ralfkonrad.quantlib` (see `java/src/main/java/module-info.java`)
- Supported platforms: Linux (amd64/arm64), macOS (arm64), Windows (amd64)

## Project Structure

```
quantlib_for_maven/
├── CMakeLists.txt                    # Root build config (C++17, Boost, SWIG versions)
├── CMakePresets.json                 # Configure/build presets (Ninja generator)
├── AGENTS.md                         # THIS FILE — canonical AI agent instructions
├── code-smells.md                    # Known code smells and design decisions
├── HOWTO-BUILD-FROM-SOURCE.md        # Detailed build guide
├── swig/                             # Project-specific SWIG interface files
│   ├── QuantLibEntrypoint.i          # SWIG entrypoint (AutoCloseable, JNI loading)
│   └── CMakeLists.txt                # SWIG/CMake integration
├── java/                             # Maven project (Java bindings + tests)
│   ├── pom.xml                       # Maven config (JDK 17 default, profiles for 21/25)
│   ├── mvnw / mvnw.cmd              # Maven wrapper
│   ├── src/main/java/
│   │   ├── module-info.java          # Java module descriptor
│   │   └── org/quantlib/helpers/     # JNI helpers, AutoCloseable, native loading
│   └── src/test/java/org/quantlib/   # JUnit 5 tests
├── external/                         # Git submodules (READ-ONLY, never edit)
│   ├── QuantLib/                     # C++ library (includes .clang-tidy, .clang-format)
│   └── QuantLib-SWIG/                # SWIG interface definitions
├── .github/
│   ├── copilot-instructions.md       # GitHub Copilot instructions (references this file)
│   ├── instructions/                 # Path-specific Copilot instructions
│   └── workflows/                    # CI/CD (GitHub Actions)
└── java/target/generated-*/          # SWIG-generated files (not committed)
    ├── generated-sources/swig/       # Generated Java sources
    └── generated-resources/swig/     # Generated native libraries
```

## Do NOT

- Edit files under `external/` — these are read-only git submodules
- Edit SWIG-generated files in `java/target/`
- Edit `external/QuantLib-SWIG/SWIG/` interface files
- Call `System.loadLibrary()` manually — JNI loads automatically
- Use wildcard imports in Java
- Use `std::make_shared` in C++ — use `ext::make_shared` instead
- Suppress type errors or swallow exceptions

## Build Commands

### Prerequisites

- CMake 4.0.0+, SWIG 4.4.1 (exact), Boost 1.90.0, JDK 17/21/25, Maven 3.8+, Ninja

### Full Build (C++ + SWIG + Java)

```bash
cmake --preset release -L
cmake --build --preset release -v
```

### Java-Only Build (after native build)

```bash
cd java
./mvnw compile
```

### Install to Local Maven Repository

```bash
cd java
./mvnw install
```

## Test Commands

### Run All Java Tests

```bash
cd java
./mvnw test
```

### Run a Single Test Class

```bash
cd java
./mvnw test -Dtest=QuantLibDateToLocalDateTest
```

### Run a Single Test Method

```bash
cd java
./mvnw test -Dtest=QuantLibDateToLocalDateTest#testQuantLibDateToLocalDate
```

### Verbose Test Output

```bash
cd java
./mvnw test -Dsurefire.useFile=false
```

## Linting and Static Checks

### C++ (QuantLib clang-tidy in submodule)

- Config: `external/QuantLib/.clang-tidy`
- Check groups: `bugprone-*`, `clang-analyzer-*`, `cppcoreguidelines-*`,
  `misc-*`, `modernize-*`, `performance-*`, `readability-*` (with exclusions)
- Notable rule: prefer `ext::make_shared` instead of `std::make_shared`

### Maven POM Validation

```bash
cd java
./mvnw validate
```

- `sortpom-maven-plugin` enforces POM element ordering

## Code Style Guidelines

### C++ Style (QuantLib conventions)

- Standard: C++17
- Indent: 4 spaces; line limit 100
- Pointer/reference alignment: `T& x`, `T* p`
- Namespace blocks are indented
- Template declarations: break line after `template <...>`

#### C++ Include Order

```cpp
#include "local_header.hpp"     // 1. Local headers
#include <ql/something.hpp>     // 2. QuantLib headers
#include <boost/something.hpp>  // 3. Boost headers
#include <standard_header>      // 4. Standard library
```

#### C++ Naming

- Types/classes: `PascalCase`
- Methods/functions: `camelCase`
- Members: trailing underscore (`payoff_`)
- Namespaces: `PascalCase` (e.g., `QuantLib`)

#### C++ Error Handling

- Use `QL_REQUIRE(condition, message)` for preconditions
- Throw/propagate `QuantLib::Error`

### Java Style

- Indent: 4 spaces; line limit 100; final newline required
- Imports: no wildcard imports; group by standard, third-party, project
  (enforced via `ij_java_class_count_to_use_import_on_demand = 9999` in `.editorconfig`)
- Types: prefer interfaces (`List`, `Map`) in signatures
- Formatting: IntelliJ defaults; keep chaining readable

#### Java Naming

- Classes: `PascalCase`
- Methods/fields: `camelCase`
- Constants: `UPPER_SNAKE_CASE`
- Packages: lowercase (`org.quantlib`)
- Tests: suffix `Test` and mirror class under test

#### Java Error Handling

- Use project-specific exceptions:
  - `UnsupportedOperatingSystemException` — thrown when the OS is not Linux, macOS, or Windows
  - `NativeLibraryLoaderException` — thrown when the JNI native library fails to load
  (both defined in `org.quantlib.helpers.QuantLibJNIHelpers`)
- Wrap native resources in try-with-resources (SWIG classes are AutoCloseable)
- Avoid swallowing exceptions; log or rethrow with context

### XML/YAML/Markdown

- Indent: 2 spaces; line limit 100 (enforced by root `.editorconfig`)

## Important Patterns

### AutoCloseable Pattern (Java)

SWIG-generated classes implement `AutoCloseable` via
`org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable`. Every QuantLib Java object holds a
native C++ pointer invisible to the JVM garbage collector. Always use `try-with-resources`
to release native memory promptly:

```java
try (var date = new Date(14, Month.April, 2023)) {
    // use date
}
```

### Prefer the `var` keyword wherever possible (Java)

The minimum JDK version is 17.
Use the `var` keyword in Java for local variable type inference.

### Prefer LocalDate over org.quantlib.Date (Java)

When writing new JUnit tests, define dates as `java.time.LocalDate`.
Use `Date.of(LocalDate)` and `Date.toLocalDate()` to convert between them:

```java
var localDate = LocalDate.of(2023, 4, 14);
try (var qlDate = Date.of(localDate)) {
    assertEquals(localDate, qlDate.toLocalDate());
}
```

### JNI Library Loading

The native library loads automatically when any QuantLib class is first used
(via a static initializer in the SWIG-generated `QuantLibJNI` class that calls
`QuantLibJNIHelpers.loadLibrary()`). Do not call `System.loadLibrary()` manually.

### SWIG Interface Files

- Project-specific SWIG code: `swig/QuantLibEntrypoint.i`
- SWIG-generated Java sources: `java/target/generated-sources/swig/org/quantlib/`
- SWIG-generated native libraries: `java/target/generated-resources/swig/native/`
- These directories are automatically added to the Maven build path via `build-helper-maven-plugin`
- Generated files are preserved during `mvn clean` to avoid rebuilding native code unnecessarily
- Never edit `external/QuantLib-SWIG/SWIG/`

## Additional Context

- Known issues and design decisions are documented in `code-smells.md`
- Detailed build instructions: `HOWTO-BUILD-FROM-SOURCE.md`

## Tool-Specific Configuration

- GitHub Copilot: `.github/copilot-instructions.md` + `.github/instructions/*.instructions.md`
- Cursor: not configured
