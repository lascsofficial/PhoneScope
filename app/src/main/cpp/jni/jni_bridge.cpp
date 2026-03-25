#include <jni.h>
#include <string>
#include <android/log.h>
#include <nlohmann/json.hpp>
#include "cpu_inspector.h"
#include "memory_inspector.h"
#include "gpu_inspector.h"
#include "thermal_inspector.h"
#include "kernel_inspector.h"
#include "storage_benchmark.h"
#include "ai_inspector.h"
#include "process_inspector.h"
#include "prop_reader.h"

#define LOG_TAG "PhoneScopeNative"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

// ═══════════════════════════════════════════════════════
// PhoneScope JNI Bridge
// All functions return JSON strings to minimize JNI
// complexity. Kotlin deserializes via kotlinx.serialization.
// ═══════════════════════════════════════════════════════

using namespace phonescope;

static jstring toJstring(JNIEnv* env, const std::string& str) {
    return env->NewStringUTF(str.c_str());
}

extern "C" {

// ── Tier A: Native Engine Inspectors ──

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetCpuInfo(
    JNIEnv* env, jclass /* clazz */) {
    LOGD("nativeGetCpuInfo called");
    return toJstring(env, CpuInspector::inspect());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetMemoryInfo(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, MemoryInspector::inspect());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetGpuInfo(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, GpuInspector::inspect());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetThermalZones(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, ThermalInspector::inspect());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetKernelInfo(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, KernelInspector::inspect());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeRunStorageBenchmark(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, StorageBenchmark::run());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetNnapiInfo(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, AiInspector::inspect());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetProcessList(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, ProcessInspector::inspect());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeGetAllSystemProps(
    JNIEnv* env, jobject /* this */) {
    auto props = PropReader::getAllProps();
    nlohmann::json j(props);
    return toJstring(env, j.dump());
}

// ── Live Monitor (called every 1 second) ──

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeSampleCpu(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, CpuInspector::sample());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeSampleMemory(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, MemoryInspector::sample());
}

JNIEXPORT jstring JNICALL
Java_com_phonescope_inspector_data_jni_NativeEngine_nativeSampleThermal(
    JNIEnv* env, jobject /* this */) {
    return toJstring(env, ThermalInspector::sample());
}

} // extern "C"
