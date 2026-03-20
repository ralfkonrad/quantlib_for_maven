---
applyTo: "external/**"
---

# External Submodules — READ ONLY

Do NOT edit any files under `external/`. These are git submodules pointing to upstream
repositories:

- `external/QuantLib/` — the QuantLib C++ library (github.com/lballabio/QuantLib)
- `external/QuantLib-SWIG/` — SWIG interface definitions (github.com/lballabio/QuantLib-SWIG)

If you need to change behavior provided by these submodules, make the change in
project-owned files instead:

- SWIG customizations: `swig/QuantLibEntrypoint.i`
- CMake customizations: root `CMakeLists.txt` or `swig/CMakeLists.txt`
- Java helpers: `java/src/main/java/org/quantlib/helpers/`
