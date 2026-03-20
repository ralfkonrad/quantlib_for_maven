---
applyTo: "swig/**/*.i"
---

# SWIG Interface File Conventions

- The project entrypoint is `swig/QuantLibEntrypoint.i` — this is the only SWIG file you
  should edit in this repository
- Never edit files in `external/QuantLib-SWIG/SWIG/` — those belong to the upstream submodule
- SWIG version must be exactly 4.4.1 (enforced by CMake `find_package(SWIG ... EXACT)`)
- All generated proxy classes implement `AutoCloseable` via the `SWIGTYPE` typemap
  and `org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable`
- The `QL_JAVA_INTERFACES` macro adds `AutoCloseable` to `std::vector` wrappers
- Generated Java sources go to `java/target/generated-sources/swig/org/quantlib/`
- Generated native libraries go to `java/target/generated-resources/swig/native/`
- Use `ext::make_shared` instead of `std::make_shared` in any C++ code
