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
list(APPEND CMAKE_MODULE_PATH "${CMAKE_CURRENT_SOURCE_DIR}/ql/QuantLib/cmake")

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 17)

set(Boost_NO_WARN_NEW_VERSIONS ON)

add_subdirectory(ql/QuantLib)

include(Platform)

# Project shared libs ON for UNIX
if (NOT DEFINED BUILD_SHARED_LIBS)
    set(BUILD_SHARED_LIBS ${UNIX})
endif()

# Boost static libs ON for MSVC
if (NOT DEFINED Boost_USE_STATIC_LIBS)
    set(Boost_USE_STATIC_LIBS ${MSVC})
endif()

# Boost static runtime ON for MSVC
if (NOT DEFINED Boost_USE_STATIC_RUNTIME)
    set(Boost_USE_STATIC_RUNTIME ${MSVC})
endif()

find_package(Boost REQUIRED COMPONENTS thread)
add_compile_definitions(BOOST_ALL_NO_LIB)

set(THREADS_PREFER_PTHREAD_FLAG ON)
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
        OUTFILE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/cpp
        SOURCES ${SWIG_QUANTLIB_I_FILE})

target_compile_options(ql_swig PRIVATE /bigobj)
if (NOT Boost_USE_STATIC_LIBS)
    target_compile_definitions(ql_test_suite PRIVATE BOOST_ALL_DYN_LINK)
endif()
