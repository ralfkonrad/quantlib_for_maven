# How to build the maven artifact from source code

## Quick Start for the impatient

To build the Maven artifact from source code, you can use the following commands:

```shell
git clone https://github.com/ralfkonrad/quantlib_for_maven.git
cd quantlib_for_maven
cmake --preset release -L
cmake --build --preset release
```

If your system meets all the requirements underneath, the Maven artifact should be built
successfully.

If you want to install the Maven artifact in your local Maven repository, you can use the following
command:

```shell
cd java
./mvnw install
```

This will install the Maven artifact with version
`io.github.ralfkonrad.quantlib_for_maven:quantlib:0.1.0-SNAPSHOT`
into your local Maven repository.

## Requirements

To build the Maven artifact from source,
you need the following tools and libraries
installed on your system:

- **A C++ Compiler**:
  A modern C++ compiler that supports C++17 or later (e.g., GCC, Clang, MSVC).

- **The Boost C++ libraries**:
  The QuantLib library depends on the Boost C++ libraries. Therefore, you need to have the Boost C++
  libraries installed on your system. Only the header files are required to build the QuantLib SWIG.

  We are trying to use the latest version of the Boost C++ libraries.
  Currently, we build the maven artifact using version `v1.86.0`.

  See the [Boost C++ Libraries](https://www.boost.org/) website for more information.

- **SWIG**:
  We are using SWIG to generate the Java bindings for QuantLib. Therefore, you need to have SWIG
  installed on your system.

  We are trying to use the latest version of the SWIG binaries.
  Currently, we build the maven artifact using version `v4.2.1`.

  See the [SWIG](http://www.swig.org/) website for more information.

- **CMake**:
  We are using the CMake C++ build system to build the QuantLib library and the Java bindings.
  Therefore, you need to have CMake installed on your system.

  See the [CMake](https://cmake.org/) website for more information.

- **A Java Development Kit (JDK)**:
  You need a Java Development Kit (JDK) to build the Java bindings and the Maven artifact.

  We are supporting the latest LTS versions of the JDK which are currently `jdk17` and `jdk21`.

  We recommend using the Temurin JDK. See the [AdoptOpenJDK](https://adoptopenjdk.net/) website
  for more information.

- **Apache Maven**:
  We are using Apache Maven to build the Maven artifact.

  See the [Apache Maven](https://maven.apache.org/) website for more information.

Ensure that all these dependencies are correctly installed and configured in your system's PATH.
