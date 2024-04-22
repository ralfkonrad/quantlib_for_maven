cmake_minimum_required(VERSION 3.25.0)

# For MSVC RUNTIME LIBRARY, need CMP0091=NEW and cmake 3.15+
cmake_policy(SET CMP0091 NEW)

project(quantlib_for_maven VERSION 1.34.0 LANGUAGES CXX)

cmake_path(SET QUANTLIB_ROOT                   external/QuantLib)

cmake_path(SET QUANTLIB_ROOT_DIR               ${CMAKE_SOURCE_DIR}/${QUANTLIB_ROOT})
cmake_path(SET QUANTLIB_GENERATED_HEADERS_DIR  ${CMAKE_BINARY_DIR}/${QUANTLIB_ROOT})

cmake_path(SET QUANTLIB_SWIG_ROOT_DIR          ${CMAKE_SOURCE_DIR}/external/QuantLib-SWIG/SWIG)

cmake_path(SET SWIG_ROOT                       swig)
cmake_path(SET QL_ENTRYPOINT_I_FILE            ${CMAKE_SOURCE_DIR}/${SWIG_ROOT}/QuantLibEntrypoint.i)

cmake_path(SET SWIG_CPP_DIR                    ${CMAKE_BINARY_DIR}/cpp)

cmake_path(SET SWIG_JAVA_DIR                   ${CMAKE_SOURCE_DIR}/java)
cmake_path(SET SWIG_JAVA_SRC_DIR               ${SWIG_JAVA_DIR}/src/main)
cmake_path(SET SWIG_JAVA_PACKAGE_DIR           ${SWIG_JAVA_SRC_DIR}/java/org/quantlib)
cmake_path(SET SWIG_JAVA_RESOURCES_DIR         ${SWIG_JAVA_SRC_DIR}/resources)
cmake_path(SET SWIG_JAVA_TARGET_DIR           ${SWIG_JAVA_DIR}/target)

# Path for package-local cmake modules
list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)
list(APPEND CMAKE_MODULE_PATH ${QUANTLIB_ROOT_DIR}/cmake)

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 14)

# we need a static QuantLib library to link against
set(BUILD_SHARED_LIBS OFF)

set(Boost_NO_WARN_NEW_VERSIONS ON)

if (${MSVC})
    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")
endif ()

add_subdirectory(${QUANTLIB_ROOT})
add_subdirectory(${SWIG_ROOT})
