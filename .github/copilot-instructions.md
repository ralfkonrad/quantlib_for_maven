# Copilot Instructions for quantlib_for_maven

This repository contains Java bindings for the QuantLib C++ library, built with SWIG and CMake,
and published as a Maven artifact.

## Project Overview

- **Repository**: `quantlib_for_maven`
- **Purpose**: Java bindings for the QuantLib C++ library
- **Maven coordinates**: `io.github.ralfkonrad.quantlib_for_maven:quantlib`
- **Languages**: C++17 (native), Java 17+ (bindings/tests)
- **SWIG entrypoint**: `swig/QuantLibEntrypoint.i`
- **Generated sources**:
  - Java: `java/target/generated-sources/swig/`
  - Native libraries: `java/target/generated-resources/swig/native/`
- **Important**: Submodules in `external/` are read-only, never edit them

## Build Commands

### Prerequisites

CMake 4.0.0+, SWIG 4.4.1 (exact), Boost 1.90.0, JDK 17/21/25, Maven 3.8+, Ninja

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
- Types: prefer interfaces (`List`, `Map`) in signatures
- Formatting: IntelliJ defaults; keep chaining readable

#### Java Naming

- Classes: `PascalCase`
- Methods/fields: `camelCase`
- Constants: `UPPER_SNAKE_CASE`
- Packages: lowercase (`org.quantlib`)
- Tests: suffix `Test` and mirror class under test

#### Java Error Handling

- Use project-specific exceptions (e.g., `UnsupportedOperatingSystemException`)
- Wrap native resources in try-with-resources (SWIG classes are AutoCloseable)
- Avoid swallowing exceptions; log or rethrow with context

### XML/YAML/Markdown

- Indent: 2 spaces; line limit 100

## Important Patterns

### AutoCloseable Pattern (Java)

SWIG-generated classes implement `AutoCloseable`:

```java
try (var date = new Date(14, Month.April, 2023)) {
    // use date
}
```

### Prefer the `var` keyword (Java)

The minimum JDK version is 17, so use `var` for local variable type inference.

### Prefer LocalDate over org.quantlib.Date (Java)

When writing JUnit tests, define dates as `java.time.LocalDate`.
Use `Date.of(LocalDate)` and `Date.toLocalDate()` to convert between them.

### JNI Library Loading

The native library loads automatically when any QuantLib class is first used.
Do not call `System.loadLibrary()` manually.

### SWIG Interface Files

- Project-specific SWIG code: `swig/QuantLibEntrypoint.i`
- SWIG-generated files are in `java/target/` directories
- These directories are automatically added to the Maven build path
- Generated files are preserved during `mvn clean` to avoid rebuilding native code
- Never edit `external/QuantLib-SWIG/SWIG/`

## Linting and Static Checks

### C++ (QuantLib clang-tidy)

- Check groups: `bugprone-*`, `clang-analyzer-*`, `cppcoreguidelines-*`, `misc-*`,
  `modernize-*`, `performance-*`, `readability-*` (with exclusions)
- Notable rule: prefer `ext::make_shared` instead of `std::make_shared`

### Maven POM Validation

```bash
cd java
./mvnw validate
```

- `sortpom-maven-plugin` enforces POM ordering

## Additional Resources

For more detailed information, see `AGENTS.md` in the repository root.
