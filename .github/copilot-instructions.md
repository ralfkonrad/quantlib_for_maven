# Copilot Instructions for quantlib_for_maven

This repository contains Java bindings for the QuantLib C++ library, built with SWIG and CMake,
and published as a Maven artifact.

**Canonical reference**: See `AGENTS.md` in the repository root for the full project guide
(structure, build commands, test commands, all conventions). This file provides Copilot-specific
guidance and quick context for code generation.

## Quick Context

- **Languages**: C++17 (native), Java 17+ (bindings/tests)
- **Test framework**: JUnit 5 (Jupiter)
- **Java module**: `io.github.ralfkonrad.quantlib` (see `java/src/main/java/module-info.java`)
- **Maven coordinates**: `io.github.ralfkonrad.quantlib_for_maven:quantlib`
- **Supported platforms**: Linux (amd64/arm64), macOS (arm64), Windows (amd64)

## Do NOT

- Edit files under `external/` — these are read-only git submodules
- Edit SWIG-generated files in `java/target/`
- Call `System.loadLibrary()` manually — JNI loads automatically
- Use wildcard imports in Java
- Use `std::make_shared` in C++ — use `ext::make_shared` instead
- Suppress type errors or swallow exceptions

## Build & Test (Quick Reference)

```bash
# Full build (C++ + SWIG + Java)
cmake --preset release -L
cmake --build --preset release -v

# Java-only (after native build)
cd java && ./mvnw compile

# Run all tests
cd java && ./mvnw test

# Run single test class
cd java && ./mvnw test -Dtest=QuantLibDateToLocalDateTest

# POM validation (sortpom ordering)
cd java && ./mvnw validate
```

## Code Generation Patterns

### Java: AutoCloseable + try-with-resources (CRITICAL)

Every QuantLib Java object holds a native C++ pointer invisible to the JVM garbage collector.
Always use `try-with-resources`:

```java
try (var date = new Date(14, Month.April, 2023)) {
    // use date
}
```

### Java: Prefer `var` keyword

The minimum JDK version is 17. Use `var` for local variable type inference.

### Java: Prefer LocalDate for dates in new tests

When writing new JUnit tests, define dates as `java.time.LocalDate`.
Use `Date.of(LocalDate)` and `Date.toLocalDate()` to convert:

```java
var localDate = LocalDate.of(2023, 4, 14);
try (var qlDate = Date.of(localDate)) {
    assertEquals(localDate, qlDate.toLocalDate());
}
```

### Java: Error handling

Use project-specific exceptions from `org.quantlib.helpers.QuantLibJNIHelpers`:
- `UnsupportedOperatingSystemException` — unsupported OS
- `NativeLibraryLoaderException` — JNI library load failure

### Java: No wildcard imports

Enforced via `ij_java_class_count_to_use_import_on_demand = 9999` in `.editorconfig`.

### C++: QuantLib conventions

- Indent: 4 spaces; line limit 100
- Include order: local → QuantLib → Boost → standard library
- Naming: `PascalCase` types, `camelCase` methods, trailing underscore members (`payoff_`)
- Error handling: `QL_REQUIRE(condition, message)`, throw `QuantLib::Error`

### XML/YAML/Markdown

- Indent: 2 spaces; line limit 100

## Path-Specific Instructions

See `.github/instructions/` for scoped guidance:
- `java-tests.instructions.md` — JUnit test conventions
- `swig.instructions.md` — SWIG interface file rules
- `external.instructions.md` — read-only submodule guard

## Additional Context

- Known issues and design decisions: `code-smells.md`
- Full project guide: `AGENTS.md`
- Build from source: `HOWTO-BUILD-FROM-SOURCE.md`
