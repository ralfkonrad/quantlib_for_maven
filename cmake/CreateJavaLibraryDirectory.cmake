cmake_path(SET QL_MVN_SWIG_JAVA_RESOURCES_NATIVE_DIR ${QL_MVN_SWIG_JAVA_RESOURCES_DIR}/native)

# Create directory for SWIG-generated Java sources
file(MAKE_DIRECTORY ${QL_MVN_SWIG_JAVA_PACKAGE_DIR})

if (WIN32)
    if (CMAKE_SIZEOF_VOID_P EQUAL 4)
        message(FATAL_ERROR "Windows x86 (32-bit) is not supported; please use an amd64 (64-bit) toolchain/generator.")
    endif ()
    cmake_path(APPEND QL_MVN_SWIG_JAVA_RESOURCES_NATIVE_DIR windows/amd64)
elseif (CMAKE_SYSTEM_NAME STREQUAL "Linux")
    cmake_path(APPEND QL_MVN_SWIG_JAVA_RESOURCES_NATIVE_DIR linux)
elseif (APPLE)
    cmake_path(APPEND QL_MVN_SWIG_JAVA_RESOURCES_NATIVE_DIR macos)
else ()
    message(FATAL_ERROR "Unsupported platform '${CMAKE_SYSTEM_NAME}'...")
endif ()

file(MAKE_DIRECTORY ${QL_MVN_SWIG_JAVA_RESOURCES_NATIVE_DIR})
