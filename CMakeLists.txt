cmake_minimum_required(VERSION 3.25.0)

# For MSVC RUNTIME LIBRARY, need CMP0091=NEW and cmake 3.15+
cmake_policy(SET CMP0091 NEW)

project(quantlib_for_maven VERSION 1.29.0 LANGUAGES CXX)

cmake_path(SET QUANTLIB_ROOT "extern/QuantLib")
cmake_path(SET QUANTLIB_SWIG_ROOT "extern/QuantLib-SWIG")
cmake_path(SET QUANTLIB_SWIG_FILES "${QUANTLIB_SWIG_ROOT}/SWIG/quantlib.i")

cmake_path(SET SWIG_WRAPPER_ROOT "wrapper")
cmake_path(SET SWIG_WRAPPER_CPP_DIR "${SWIG_WRAPPER_ROOT}/cpp")

cmake_path(SET SWIG_WRAPPER_MAVEN_DIR "${SWIG_WRAPPER_ROOT}/maven")
cmake_path(SET SWIG_WRAPPER_JAVA_DIR "${SWIG_WRAPPER_MAVEN_DIR}/src/main")
cmake_path(SET SWIG_WRAPPER_JAVA_PACKAGE_DIR "${SWIG_WRAPPER_JAVA_DIR}/java/org/quantlib")
cmake_path(SET SWIG_WRAPPER_JAVA_RESOURCES_DIR "${SWIG_WRAPPER_JAVA_DIR}/resources/org/quantlib")

set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>")

# Path for package-local cmake modules
list(APPEND CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake")
list(APPEND CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/${QUANTLIB_ROOT}/cmake")

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 17)

set(Boost_NO_WARN_NEW_VERSIONS ON)

add_subdirectory(${QUANTLIB_ROOT})
include_directories(SYSTEM ${CMAKE_BINARY_DIR}/${QUANTLIB_ROOT} ${CMAKE_SOURCE_DIR}/${QUANTLIB_ROOT})
link_libraries(ql_library)

include(Platform)
if (${MSVC})
    add_compile_options(/bigobj)
endif ()

# Boost static libs ON for MSVC
if (NOT DEFINED Boost_USE_STATIC_LIBS)
    set(Boost_USE_STATIC_LIBS ${MSVC})
endif ()

# Boost static runtime ON for MSVC
if (NOT DEFINED Boost_USE_STATIC_RUNTIME)
    set(Boost_USE_STATIC_RUNTIME ${MSVC})
endif ()

find_package(Boost REQUIRED COMPONENTS thread)
add_compile_definitions(BOOST_ALL_NO_LIB)
if (NOT Boost_USE_STATIC_LIBS)
    add_compile_definitions(BOOST_ALL_DYN_LINK)
endif ()
include_directories(SYSTEM ${Boost_INCLUDE_DIRS})
link_directories(${Boost_LIBRARY_DIRS})

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)
link_libraries(Threads::Threads)

find_package(JNI REQUIRED)
include_directories(SYSTEM ${JNI_INCLUDE_DIRS})

find_package(SWIG 4.1.1 EXACT REQUIRED COMPONENTS java)
include(UseSWIG)

set_property(SOURCE ${CMAKE_SOURCE_DIR}/${QUANTLIB_SWIG_FILES} PROPERTY CPLUSPLUS ON)

swig_add_library(
        QuantLibJNI
        TYPE SHARED
        LANGUAGE java
        OUTFILE_DIR ${CMAKE_SOURCE_DIR}/${SWIG_WRAPPER_CPP_DIR}
        OUTPUT_DIR ${CMAKE_SOURCE_DIR}/${SWIG_WRAPPER_JAVA_PACKAGE_DIR}
        SOURCES ${CMAKE_SOURCE_DIR}/${QUANTLIB_SWIG_FILES})

set_property(TARGET QuantLibJNI PROPERTY
        SWIG_COMPILE_OPTIONS -package org.quantlib)

set_property(TARGET QuantLibJNI PROPERTY
        SWIG_COMPILE_DEFINITIONS JAVA_AUTOCLOSEABLE)

set_target_properties(QuantLibJNI PROPERTIES
        VERSION ${PROJECT_VERSION}
        SOVERSION ${PROJECT_VERSION})

add_custom_command(TARGET QuantLibJNI POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E make_directory "${CMAKE_SOURCE_DIR}/${SWIG_WRAPPER_JAVA_RESOURCES_DIR}"
        COMMAND ${CMAKE_COMMAND} -E copy "$<TARGET_FILE:QuantLibJNI>" "${CMAKE_SOURCE_DIR}/${SWIG_WRAPPER_JAVA_RESOURCES_DIR}"
        COMMENT "Copying $<TARGET_FILE:QuantLibJNI> to ${CMAKE_SOURCE_DIR}/${SWIG_WRAPPER_JAVA_RESOURCES_DIR}.")