cmake_minimum_required(VERSION 3.25.0)

# Target specific architectures for macOS and iOS, enable dual builds both for arm64 and amd64 architectures
# According to the documentation:
#   The value of this variable should be set prior to the first project() or enable_language() command
#   invocation because it may influence configuration of the toolchain and flags
set(CMAKE_OSX_ARCHITECTURES arm64 x86_64)

project(quantlib_for_maven VERSION 1.38.0 LANGUAGES CXX)

# set the Boost and Swig versions we want to use
# to ensure compatibility across linux, macos and windows builds
set(QL_MVN_BOOST_VERSION "1.88.0" CACHE STRING "Boost version we want to use to ensure compatibility")
set(QL_MVN_SWIG_VERSION "4.3.1" CACHE STRING "Swig version we want to use to ensure compatibility")

# set the QuantLib options the way we need them.
# So, they do not need to be provided in the presets or as a cmake -D cache entry.
set(QL_ENABLE_THREAD_SAFE_OBSERVER_PATTERN ON CACHE BOOL "Needed for java's garbage collector" FORCE)
set(QL_THROW_IN_CYCLES ON CACHE BOOL "Throw an exception when a notification loop is detected")
set(QL_FASTER_LAZY_OBJECTS OFF CACHE BOOL "Cause lazy objects to forward just the first notification instead of every one")

# paths needed throughout the build
cmake_path(SET QL_MVN_QUANTLIB_ROOT external/QuantLib)

cmake_path(SET QL_MVN_QUANTLIB_ROOT_DIR ${CMAKE_SOURCE_DIR}/${QL_MVN_QUANTLIB_ROOT})
cmake_path(SET QL_MVN_QUANTLIB_GENERATED_HEADERS_DIR ${CMAKE_BINARY_DIR}/${QL_MVN_QUANTLIB_ROOT})

cmake_path(SET QL_MVN_QUANTLIB_SWIG_ROOT_DIR ${CMAKE_SOURCE_DIR}/external/QuantLib-SWIG/SWIG)

cmake_path(SET QL_MVN_SWIG_ROOT swig)
cmake_path(SET QL_MVN_ENTRYPOINT_I_FILE ${CMAKE_SOURCE_DIR}/${QL_MVN_SWIG_ROOT}/QuantLibEntrypoint.i)

cmake_path(SET QL_MVN_SWIG_CPP_DIR ${CMAKE_BINARY_DIR}/cpp)

cmake_path(SET QL_MVN_SWIG_JAVA_DIR ${CMAKE_SOURCE_DIR}/java)
cmake_path(SET QL_MVN_SWIG_JAVA_SRC_DIR ${QL_MVN_SWIG_JAVA_DIR}/src/main)
cmake_path(SET QL_MVN_SWIG_JAVA_PACKAGE_DIR ${QL_MVN_SWIG_JAVA_SRC_DIR}/java/org/quantlib)
cmake_path(SET QL_MVN_SWIG_JAVA_RESOURCES_DIR ${QL_MVN_SWIG_JAVA_SRC_DIR}/resources)
cmake_path(SET QL_MVN_SWIG_JAVA_TARGET_DIR ${QL_MVN_SWIG_JAVA_DIR}/target)

# Path for package-local cmake modules
list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)
list(APPEND CMAKE_MODULE_PATH ${QL_MVN_QUANTLIB_ROOT_DIR}/cmake)

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 17)

# we need a static QuantLib library to link against
set(BUILD_SHARED_LIBS OFF)

set(Boost_NO_WARN_NEW_VERSIONS ON)

if (${MSVC})
    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
endif ()

# The FindBoost module is removed in cmake 3.30, finding the upstream BoostConfig.cmake
if (POLICY CMP0167)
    find_package(Boost ${QL_MVN_BOOST_VERSION} EXACT CONFIG REQUIRED)
else ()
    find_package(Boost ${QL_MVN_BOOST_VERSION} EXACT REQUIRED)
endif ()

add_subdirectory(${QL_MVN_QUANTLIB_ROOT})
add_subdirectory(${QL_MVN_SWIG_ROOT})
