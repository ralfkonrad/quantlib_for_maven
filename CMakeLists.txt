cmake_minimum_required(VERSION 3.24.0)

# For MSVC RUNTIME LIBRARY, need CMP0091=NEW and cmake 3.15+
cmake_policy(SET CMP0091 NEW)

project(quantlib_for_maven VERSION 1.29.0 LANGUAGES CXX)

set(SWIG_QUANTLIB_I_FILE
        "${CMAKE_CURRENT_SOURCE_DIR}/ql/QuantLib-SWIG/SWIG/quantlib.i"
        CACHE
        FILEPATH
        "The root source file for the QuantLib swig generation")

set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")

# Path for package-local cmake modules
list(APPEND CMAKE_MODULE_PATH "${CMAKE_CURRENT_SOURCE_DIR}/cmake")

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 17)

add_subdirectory(ql/QuantLib)

find_package(Boost REQUIRED)
find_package(Threads REQUIRED)
find_package(JNI REQUIRED)

link_directories(${Boost_LIBRARY_DIRS})

include_directories(
        SYSTEM
        ${CMAKE_BINARY_DIR}/ql/QuantLib
        ql/QuantLib
        ${Boost_INCLUDE_DIRS}
        ${JNI_INCLUDE_DIRS}
)
link_libraries(
        ql_library
        Threads::Threads
)

find_package(SWIG 4.1 REQUIRED COMPONENTS java)
include(UseSWIG)

set_property(SOURCE ${SWIG_QUANTLIB_I_FILE} PROPERTY CPLUSPLUS ON)
swig_add_library(
        ql_swig
        TYPE SHARED
        LANGUAGE java
        OUTPUT_DIR ${CMAKE_CURRENT_SOURCE_DIR}/java
        OUTFILE_DIR ${CMAKE_CURRENT_SOURCE_DIR}//cpp
        SOURCES ${SWIG_QUANTLIB_I_FILE})

target_compile_options(ql_swig PRIVATE /bigobj)
