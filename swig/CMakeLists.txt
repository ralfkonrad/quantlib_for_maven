include(CreateJavaLibraryDirectory)

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

find_package(Boost REQUIRED)
add_compile_definitions(BOOST_ALL_NO_LIB)
if (NOT Boost_USE_STATIC_LIBS)
    add_compile_definitions(BOOST_ALL_DYN_LINK)
endif ()

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)

find_package(JNI REQUIRED)

find_package(SWIG 4.1.1 EXACT REQUIRED COMPONENTS java)
include(UseSWIG)

set_property(SOURCE ${QL_ENTRYPOINT_I_FILE} PROPERTY CPLUSPLUS ON)
set_property(SOURCE ${QL_ENTRYPOINT_I_FILE} PROPERTY INCLUDE_DIRECTORIES ${QUANTLIB_SWIG_ROOT_DIR})
set_property(SOURCE ${QL_ENTRYPOINT_I_FILE} PROPERTY USE_TARGET_INCLUDE_DIRECTORIES OFF)
set_property(SOURCE ${QL_ENTRYPOINT_I_FILE} PROPERTY SWIG_MODULE_NAME QuantLib)

swig_add_library(
        QuantLibJNI
        TYPE SHARED
        LANGUAGE java
        OUTFILE_DIR ${SWIG_CPP_DIR}
        OUTPUT_DIR ${SWIG_JAVA_PACKAGE_DIR}
        SOURCES ${QL_ENTRYPOINT_I_FILE})

set_property(TARGET QuantLibJNI PROPERTY
        SWIG_COMPILE_OPTIONS -package org.quantlib)

if (${MSVC})
    set_property(TARGET QuantLibJNI PROPERTY
            RUNTIME_OUTPUT_DIRECTORY ${SWIG_JAVA_RESOURCES_NATIVE_DIR})
else ()
    set_property(TARGET QuantLibJNI PROPERTY
            LIBRARY_OUTPUT_DIRECTORY ${SWIG_JAVA_RESOURCES_NATIVE_DIR})
endif ()

target_include_directories(QuantLibJNI SYSTEM PRIVATE
        ${QUANTLIB_GENERATED_HEADERS_DIR}
        ${QUANTLIB_ROOT_DIR}
        ${Boost_INCLUDE_DIRS}
        ${JNI_INCLUDE_DIRS})

target_link_libraries(QuantLibJNI
        ql_library
        Threads::Threads)

add_custom_command(
        TARGET QuantLibJNI POST_BUILD
        COMMAND mvn clean verify
        WORKING_DIRECTORY ${SWIG_MAVEN_DIR}
        COMMENT "Running mvn clean verify"
        VERBATIM)
