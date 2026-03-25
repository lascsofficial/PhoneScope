cmake_minimum_required(VERSION 3.22)
project(phonescope_engine CXX)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Compiler hardening flags
add_compile_options(
    -Wall -Wextra -Wpedantic
    -fstack-protector-strong
    -D_FORTIFY_SOURCE=2
    -O2
)

# ═══════════════════════════════════════════
# PhoneScope Native Engine
# ═══════════════════════════════════════════

# Create a static library for the engine code so it can be shared with tests
add_library(phonescope_core STATIC
    # Engine modules
    engine/cpu_inspector.cpp
    engine/memory_inspector.cpp
    engine/gpu_inspector.cpp
    engine/thermal_inspector.cpp
    engine/kernel_inspector.cpp
    engine/storage_benchmark.cpp
    engine/ai_inspector.cpp
    engine/process_inspector.cpp

    # Utilities
    util/proc_reader.cpp
    util/sys_reader.cpp
    util/prop_reader.cpp
    util/file_util.cpp
)

target_include_directories(phonescope_core PUBLIC
    ${CMAKE_CURRENT_SOURCE_DIR}/third_party
    ${CMAKE_CURRENT_SOURCE_DIR}/engine
    ${CMAKE_CURRENT_SOURCE_DIR}/util
)

# The JNI shared library for the Android app
add_library(phonescope_engine SHARED
    jni/jni_bridge.cpp
)

target_include_directories(phonescope_engine PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/jni
)

# Find Android system libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(GLESv2-lib GLESv2)
find_library(GLESv3-lib GLESv3)
find_library(EGL-lib EGL)
find_library(vulkan-lib vulkan)
find_library(neural-networks-lib neuralnetworks)

target_link_libraries(phonescope_engine
    phonescope_core
    ${log-lib}
    ${android-lib}
    ${GLESv2-lib}
    ${GLESv3-lib}
    ${EGL-lib}
    ${vulkan-lib}
    ${neural-networks-lib}
)

# ═══════════════════════════════════════════
# Unit Tests (gtest)
# ═══════════════════════════════════════════

include(FetchContent)
FetchContent_Declare(
    googletest
    URL https://github.com/google/googletest/archive/refs/tags/v1.14.0.zip
)
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

enable_testing()

add_executable(phonescope_tests
    tests/file_util_test.cpp
    tests/proc_reader_test.cpp
    tests/sys_reader_test.cpp
    tests/prop_reader_test.cpp
)

target_link_libraries(phonescope_tests
    phonescope_core
    gtest_main
    ${log-lib}
)
