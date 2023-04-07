cmake_minimum_required(VERSION 3.25.0)

# For MSVC RUNTIME LIBRARY, need CMP0091=NEW and cmake 3.15+
cmake_policy(SET CMP0091 NEW)

project(quantlib_for_maven VERSION 1.29.0 LANGUAGES CXX)

cmake_path(SET QUANTLIB_ROOT                   extern/QuantLib)

cmake_path(SET QUANTLIB_ROOT_DIR               ${CMAKE_SOURCE_DIR}/${QUANTLIB_ROOT})
cmake_path(SET QUANTLIB_GENERATED_HEADERS_DIR  ${CMAKE_BINARY_DIR}/${QUANTLIB_ROOT})

cmake_path(SET QUANTLIB_SWIG_ROOT_DIR          ${CMAKE_SOURCE_DIR}/extern/QuantLib-SWIG/SWIG)

cmake_path(SET SWIG_ROOT                       swig)
cmake_path(SET QL_ENTRYPOINT_I_FILE            ${CMAKE_SOURCE_DIR}/${SWIG_ROOT}/QuantLibEntrypoint.i)

cmake_path(SET SWIG_WRAPPER_ROOT_DIR           ${CMAKE_SOURCE_DIR}/wrapper)
cmake_path(SET SWIG_WRAPPER_CPP_DIR            ${SWIG_WRAPPER_ROOT_DIR}/cpp)

cmake_path(SET SWIG_MAVEN_DIR          ${SWIG_WRAPPER_ROOT_DIR}/maven)
cmake_path(SET SWIG_JAVA_DIR           ${SWIG_MAVEN_DIR}/src/main)
cmake_path(SET SWIG_JAVA_PACKAGE_DIR   ${SWIG_JAVA_DIR}/java/org/quantlib)
cmake_path(SET SWIG_JAVA_RESOURCES_DIR ${SWIG_JAVA_DIR}/resources)

if (${MSVC})
    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
endif ()

# Path for package-local cmake modules
list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)
list(APPEND CMAKE_MODULE_PATH ${QUANTLIB_ROOT_DIR}/cmake)

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 14)

set(Boost_NO_WARN_NEW_VERSIONS ON)

add_subdirectory(${QUANTLIB_ROOT})
add_subdirectory(${SWIG_ROOT})
