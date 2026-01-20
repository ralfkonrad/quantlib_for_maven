# AGENTS.md - AI Coding Agent Instructions

This document provides instructions for AI coding agents working on the quantlib_for_maven project.

## Project Overview

Java language binding for the QuantLib C++ library for quantitative finance. The project uses SWIG to generate Java bindings from the C++ library, distributed as a Maven artifact.

**Maven Coordinates:** `io.github.ralfkonrad.quantlib_for_maven:quantlib`

## Project Structure

```
quantlib_for_maven/
├── CMakeLists.txt           # Main CMake build configuration
├── CMakePresets.json        # CMake presets (release/debug)
├── cmake/                   # Custom CMake modules
├── swig/                    # SWIG interface files (project-specific)
│   └── QuantLibEntrypoint.i # Main SWIG entrypoint
├── java/                    # Java/Maven project
│   ├── pom.xml              # Maven build configuration
│   └── src/
│       ├── main/java/org/quantlib/          # SWIG-generated + helper classes
│       └── test/java/org/quantlib/          # JUnit 6 tests
├── external/                # Git submodules (DO NOT MODIFY)
│   ├── QuantLib/            # QuantLib C++ library
│   └── QuantLib-SWIG/       # QuantLib SWIG interface definitions
└── build/                   # Build output directory
```

**Important:** Files in `external/` are git submodules. Do not modify them.

## Build Commands

### Prerequisites
- CMake 4.0.0+, SWIG 4.4.1, Boost 1.90.0, JDK 17/21/25, Maven 3.8.0+, Ninja

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

### Run Tests with Verbose Output
```bash
cd java
./mvnw test -Dsurefire.useFile=false
```

## Code Style Guidelines

### C++ (QuantLib conventions)
- **Standard:** C++17
- **Indent:** 4 spaces
- **Line limit:** 100 characters
- **Pointer alignment:** Left (`T& x`, not `T &x`)
- **Namespace indentation:** All namespaces indented
- **Template declarations:** Always break after template

#### Include Order (strict)
```cpp
#include "local_header.hpp"     // 1. Local headers
#include <ql/something.hpp>     // 2. QuantLib headers
#include <boost/something.hpp>  // 3. Boost headers
#include <standard_header>      // 4. Standard library
```

#### C++ Naming Conventions
- Classes: `PascalCase` (e.g., `BlackScholesMertonProcess`)
- Methods: `camelCase` (e.g., `setupArguments`)
- Member variables: trailing underscore (e.g., `payoff_`, `exercise_`)
- Namespaces: `PascalCase` (e.g., `QuantLib`)

#### C++ Error Handling
- Use `QL_REQUIRE(condition, message)` macro for preconditions
- Throws `QuantLib::Error` exceptions

### Java Code Style
- **Line limit:** 100 characters
- **Final newline:** Required
- **Indent:** 4 spaces (from IntelliJ defaults)

#### Java Naming Conventions
- Classes: `PascalCase` (e.g., `QuantLibJNIHelpers`)
- Methods: `camelCase` (e.g., `loadLibrary`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `NATIVE_FOLDER_PATH_PREFIX`)
- Packages: lowercase (e.g., `org.quantlib`)
- Test classes: suffix with `Test` (e.g., `QuantLibDateToLocalDateTest`)

#### Java Error Handling
- Use custom exceptions (e.g., `UnsupportedOperatingSystemException`)
- Always use try-with-resources for QuantLib objects (they implement `AutoCloseable`)

### XML/YAML/Markdown
- **Indent:** 2 spaces
- **Line limit:** 100 characters

## Important Patterns

### AutoCloseable Pattern (Java)
All SWIG-generated classes implement `AutoCloseable`. Always use try-with-resources:
```java
try (var date = new Date(14, Month.April, 2023)) {
    // use date
} // automatically cleaned up
```

### JNI Library Loading
The native library is automatically loaded when any QuantLib class is first used.
Do not call `System.loadLibrary()` manually.

### SWIG Interface Files
- Project-specific SWIG code goes in `swig/QuantLibEntrypoint.i`
- Do not modify files in `external/QuantLib-SWIG/SWIG/`

## Linting and Static Analysis

### C++ (clang-tidy - in external/QuantLib/)
Enabled check categories: `bugprone-*`, `clang-analyzer-*`, `cppcoreguidelines-*`, 
`misc-*`, `modernize-*`, `performance-*`, `readability-*` (with many exclusions)

Notable: Uses `ext::make_shared` instead of `std::make_shared`

### Maven POM Validation
```bash
cd java
./mvnw validate
```
Uses sortpom-maven-plugin to enforce POM ordering.

## CMake Cache Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `QL_MVN_BOOST_VERSION` | `1.90.0` | Boost version |
| `QL_MVN_SWIG_VERSION` | `4.4.1` | SWIG version (exact match required) |
| `QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN` | `ON` | Required for Java GC |

## Supported Platforms
- **Linux:** x86_64
- **macOS:** arm64 and x86_64 (universal binary)
- **Windows:** amd64 only

## Common Tasks

### Adding New Java Helper Classes
Place in `java/src/main/java/org/quantlib/helpers/`

### Adding New Tests
1. Create test class in `java/src/test/java/org/quantlib/`
2. Use JUnit 6 annotations (`@Test`, `@BeforeEach`, etc.)
3. Name the class with `Test` suffix

### Updating QuantLib Version
1. Update git submodule in `external/QuantLib`
2. Update version in `CMakeLists.txt`
3. Rebuild completely

## Dependencies

### Runtime (Java)
- `org.slf4j:slf4j-api` - Logging facade

### Test (Java)  
- `org.junit.jupiter:junit-jupiter-engine` - JUnit 6
- `org.slf4j:slf4j-simple` - Simple logging for tests

### Build-time
- Boost C++ Libraries (headers only)
- SWIG 4.4.1 (exact version)
- JNI headers (from JDK)
