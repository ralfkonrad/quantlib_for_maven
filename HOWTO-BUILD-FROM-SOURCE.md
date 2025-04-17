# How to build the maven artifact from source code

## Quick Start for the impatient

### Recommended setup

- an up-to-date cpp compiler
- an up-to-date cmake
- an up-to-date ninja build system
- Boost C++ libraries version `v1.88.0`
- Swig version `v4.3.1`
- OpenJDK version `jdk17` or `jdk21`
- an up-to-date Apache Maven

### Build steps

To build the Maven artifact from source code, you can use the following commands:

```shell
git clone https://github.com/ralfkonrad/quantlib_for_maven.git
cd quantlib_for_maven
cmake --preset release -L
cmake --build --preset release -v
```

If your system meets all the requirements underneath, the Maven artifact should be built
successfully.

If you want to install the Maven artifact into your local Maven repository,
you can use the following command:

```shell
cd java
./mvnw install
```

This will install the Maven artifact
`io.github.ralfkonrad.quantlib_for_maven:quantlib`
with version `0.1.0-SNAPSHOT` into your local Maven repository.

## Build options

There are two presets `release` and `debug` available for the CMake build,
using `ninja` as the generator and `${sourceDir}/build/${presetName}` as the build directory.
They will work out of the box if you have all the required tools and libraries installed.

If you do not have access to the latest versions of the Boost C++ libraries or SWIG binaries,
you can use the following CMake cache variables to customize the build process:

- `QL_MVN_BOOST_VERSION`: The version of the Boost C++ libraries to use, 
                          if you do not want to use the current default `1.88.0`.

- `QL_MVN_SWIG_VERSION`: The version of the SWIG binaries to use,
                         if you do not want to use the current default `4.3.1`.

You can pass these variables to the `cmake` command using the `-D` option like this:

```shell
cd quantlib_for_maven
mkdir build
cd build
cmake -S .. -B . -DQL_MVN_BOOST_VERSION=1.83.0 -DQL_MVN_SWIG_VERSION=4.2.1 -L
cmake --build . -v
```

Also, there are three additional CMake cache variables available
which differ from their QuantLib defaults:

- `QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN`: 
  This is required to be set to `ON` and cannot be changed.

  The thread-safe observer pattern will be enabled which is required for the Java bindings as the
  Java Garbage Collector can run in a different thread than the main thread.

- `QL_THROW_IN_CYCLES`:
  This is set to `ON` by default and can be changed to `OFF`.
  See the QuantLib documentation for more information about this flag.

- `QL_FASTER_LAZY_OBJECTS`:
  This is set to `OFF` by default and can be changed to `ON`.
  See the QuantLib documentation for more information about this flag.

## Requirements

To build the Maven artifact from source,
you need the following tools and libraries
installed on your system:

- **A C++ Compiler**:
  A modern C++ compiler that supports C++17 or later (e.g., GCC, Clang, MSVC).

- **CMake**:
  We are using the CMake C++ build system to build the QuantLib library and the Java bindings.
  Therefore, you need to have CMake installed on your system.

  See the [CMake](https://cmake.org/) website for more information.

- **The Boost C++ libraries**:
  The QuantLib library depends on the Boost C++ libraries. Therefore, you need to have the Boost C++
  libraries installed on your system. Only the header files are required to build the QuantLib SWIG.

  We are trying to use the latest version of the Boost C++ libraries.
  Currently, we build the maven artifact using version `v1.88.0`.

  See the [Boost C++ Libraries](https://www.boost.org/) website for more information.

- **SWIG**:
  We are using SWIG to generate the Java bindings for QuantLib. Therefore, you need to have SWIG
  installed on your system.

  We are trying to use the latest version of the SWIG binaries.
  Currently, we build the maven artifact using version `v4.3.1`.

  See the [SWIG](http://www.swig.org/) website for more information.

- **A Java Development Kit (JDK)**:
  You need a Java Development Kit (JDK) to build the Java bindings and the Maven artifact.

  We are supporting the latest LTS versions of the JDK which are currently `jdk17` and `jdk21`.

  We recommend using the Temurin JDK. See the [AdoptOpenJDK](https://adoptopenjdk.net/) website
  for more information.

- **Apache Maven**:
  We are using Apache Maven to build the Maven artifact.

  See the [Apache Maven](https://maven.apache.org/) website for more information.

- **Ninja** (optional):
  We are using the Ninja cpp build system to build the QuantLib library and the Java bindings.

  If you want to use the given cmake presets `release` and `debug`,
  you need to have Ninja installed.

  See the [Ninja](https://ninja-build.org/) website for more information.  

Ensure that all these dependencies are correctly installed and configured in your system's PATH.
