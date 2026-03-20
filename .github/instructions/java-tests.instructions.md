---
applyTo: "java/src/test/**/*.java"
---

# Java Test Conventions

- Framework: JUnit 5 (Jupiter) — use `org.junit.jupiter.api.Test`, not JUnit 4
- Class naming: suffix `Test`, mirror the class under test (e.g., `DatesTest` for `Date`)
- Use `var` for all local variable declarations (JDK 17+)
- Use `try-with-resources` for every QuantLib object (they hold native C++ memory)
- Prefer `java.time.LocalDate` for date values; convert with `Date.of(LocalDate)` and
  `Date.toLocalDate()`
- No wildcard imports — import each class explicitly
- Indent: 4 spaces; line limit: 100; final newline required
- Use `Assertions.*` static methods from JUnit 5 (not `Assert` from JUnit 4)

Example:

```java
@Test
public void testDateConversion() {
    var localDate = LocalDate.of(2023, 4, 14);
    try (var qlDate = Date.of(localDate)) {
        assertEquals(localDate, qlDate.toLocalDate());
    }
}
```
