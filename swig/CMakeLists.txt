include(CreateJavaLibraryDirectory)

# QL's Platform.cmake sets up MSVC runtime/warnings etc.
include(Platform)

# Boost static libs ON for MSVC
if (NOT DEFINED Boost_USE_STATIC_LIBS)
    set(Boost_USE_STATIC_LIBS ${MSVC})
endif ()

# Boost static runtime ON for MSVC
if (NOT DEFINED Boost_USE_STATIC_RUNTIME)
    set(Boost_USE_STATIC_RUNTIME ${MSVC})
endif ()

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)

find_package(JNI REQUIRED)

find_package(SWIG ${QL_MVN_SWIG_VERSION} EXACT REQUIRED COMPONENTS java)
include(UseSWIG)

set_property(SOURCE ${QL_MVN_ENTRYPOINT_I_FILE} PROPERTY CPLUSPLUS ON)
set_property(SOURCE ${QL_MVN_ENTRYPOINT_I_FILE} PROPERTY SWIG_MODULE_NAME QuantLib)
set_property(SOURCE ${QL_MVN_ENTRYPOINT_I_FILE} PROPERTY COMPILE_DEFINITIONS JAVA_FINALIZER)
set_property(SOURCE ${QL_MVN_ENTRYPOINT_I_FILE} PROPERTY INCLUDE_DIRECTORIES ${QL_MVN_QUANTLIB_SWIG_ROOT_DIR})
set_property(SOURCE ${QL_MVN_ENTRYPOINT_I_FILE} PROPERTY USE_TARGET_INCLUDE_DIRECTORIES OFF)

swig_add_library(
        QuantLibJNI
        TYPE SHARED
        LANGUAGE java
        OUTFILE_DIR ${QL_MVN_SWIG_CPP_DIR}
        OUTPUT_DIR ${QL_MVN_SWIG_JAVA_PACKAGE_DIR}
        SOURCES ${QL_MVN_ENTRYPOINT_I_FILE})

# Target-scoped flags/defs instead of directory-wide globals.
if (MSVC)
    target_compile_options(QuantLibJNI PRIVATE /bigobj)
endif ()

target_compile_definitions(QuantLibJNI PRIVATE BOOST_ALL_NO_LIB)
if (NOT Boost_USE_STATIC_LIBS)
    target_compile_definitions(QuantLibJNI PRIVATE BOOST_ALL_DYN_LINK)
endif ()

set_property(TARGET QuantLibJNI PROPERTY
        SWIG_COMPILE_OPTIONS -package org.quantlib)

if (MSVC)
    set_target_properties(QuantLibJNI PROPERTIES
            RUNTIME_OUTPUT_DIRECTORY ${QL_MVN_SWIG_JAVA_RESOURCES_NATIVE_DIR})
else ()
    set_target_properties(QuantLibJNI PROPERTIES
            LIBRARY_OUTPUT_DIRECTORY ${QL_MVN_SWIG_JAVA_RESOURCES_NATIVE_DIR})
endif ()

target_include_directories(QuantLibJNI SYSTEM PRIVATE
        ${QL_MVN_QUANTLIB_GENERATED_HEADERS_DIR}
        ${QL_MVN_QUANTLIB_ROOT_DIR}
        ${Boost_INCLUDE_DIRS}
        ${JNI_INCLUDE_DIRS})

target_link_libraries(QuantLibJNI
        ql_library
        Threads::Threads)

add_custom_command(
        TARGET QuantLibJNI POST_BUILD
        COMMAND ./mvnw --batch-mode --show-version clean verify
        WORKING_DIRECTORY ${QL_MVN_SWIG_JAVA_DIR}
        COMMENT "Running './mvnw --batch-mode --show-version clean verify'."
        VERBATIM)
