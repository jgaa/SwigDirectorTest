# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

cmake_minimum_required(VERSION 3.4.1)

set(SWIG_I_FILE "src/main/cpp/NativeRestApi.i")
set(JAVA_GEN_PACKAGE "eu.lastviking.core")
string(REPLACE "." "/" JAVA_GEN_SUBDIR ${JAVA_GEN_PACKAGE})
set(JAVA_GEN_DIR ${Project_SOURCE_DIR}/src/main/java/${JAVA_GEN_SUBDIR})

include_directories(src/main/cpp)

find_package(SWIG REQUIRED)
include(${SWIG_USE_FILE})

# Remove old java files, in case we don't need to generate some of them anymore
file(REMOVE_RECURSE ${JAVA_GEN_DIR})

# Ensure file recognized as C++ (otherwise, exported as C file)
set_property(SOURCE src/main/cpp/NativeRestApi.i PROPERTY CPLUSPLUS ON)

# Setup SWIG flags and locations
set(CMAKE_SWIG_FLAGS -c++ -package ${JAVA_GEN_PACKAGE})
set(CMAKE_SWIG_OUTDIR ${JAVA_GEN_DIR})

# Export a wrapper file to Java, and link with the created C++ library
swig_add_module(NativeRestApi_Wrapper java ${SWIG_I_FILE})
swig_link_libraries(NativeRestApi_Wrapper NativeRestApi)

add_library( # Sets the name of the library.
     NativeRestApi

     # Sets the library as a shared library.
     SHARED

     # Provides a relative path to your source file(s).
     src/main/cpp/NativeRestApi.cpp
     )
