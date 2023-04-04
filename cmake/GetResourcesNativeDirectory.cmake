cmake_path(SET SWIG_WRAPPER_JAVA_RESOURCES_NATIVE_DIR ${SWIG_WRAPPER_JAVA_RESOURCES_DIR}/native)
if (${WIN32})
    cmake_path(APPEND SWIG_WRAPPER_JAVA_RESOURCES_NATIVE_DIR windows)
    if (${CMAKE_SIZEOF_VOID_P} EQUAL 4)
        cmake_path(APPEND SWIG_WRAPPER_JAVA_RESOURCES_NATIVE_DIR x86)
    else ()
        cmake_path(APPEND SWIG_WRAPPER_JAVA_RESOURCES_NATIVE_DIR x64)
    endif ()
elseif (${LINUX})
    cmake_path(APPEND SWIG_WRAPPER_JAVA_RESOURCES_NATIVE_DIR linux)
elseif (${APPLE})
    cmake_path(APPEND SWIG_WRAPPER_JAVA_RESOURCES_NATIVE_DIR macos)
else ()
    message(FATAL_ERROR "Unsupported platform ${CMAKE_SYSTEM}...")
endif ()
