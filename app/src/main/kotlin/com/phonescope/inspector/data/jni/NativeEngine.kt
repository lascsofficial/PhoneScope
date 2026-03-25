package com.phonescope.inspector.data.jni

/**
 * Kotlin JNI wrapper for the PhoneScope C++ native engine.
 * All native methods return JSON strings that are deserialized
 * on the Kotlin side via kotlinx.serialization.
 */
object NativeEngine {

    init {
        System.loadLibrary("phonescope_engine")
    }

    // ═══════════════════════════════════════════
    // Tier A — Native Engine Inspectors
    // ═══════════════════════════════════════════

    /** CPU info: topology, frequencies, caches, features, vulnerabilities */
    @JvmStatic external fun nativeGetCpuInfo(): String

    /** Memory info: /proc/meminfo + zRAM + bandwidth estimate */
    @JvmStatic external fun nativeGetMemoryInfo(): String

    /** GPU info: OpenGL ES + Vulkan queries */
    @JvmStatic external fun nativeGetGpuInfo(): String

    /** Thermal zones: all zones, trip points, cooling devices */
    @JvmStatic external fun nativeGetThermalZones(): String

    /** Kernel & OS: uname, SELinux, boot state, partitions */
    @JvmStatic external fun nativeGetKernelInfo(): String

    /** Storage benchmark: sequential + random 4K R/W */
    @JvmStatic external fun nativeRunStorageBenchmark(): String

    /** AI/NNAPI: accelerators, supported ops */
    @JvmStatic external fun nativeGetNnapiInfo(): String

    /** Process list: all /proc/[pid] with CPU/RAM/state */
    @JvmStatic external fun nativeGetProcessList(): String

    /** System properties: all ro.*, persist.*, sys.* */
    @JvmStatic external fun nativeGetAllSystemProps(): String

    // ═══════════════════════════════════════════
    // Live Monitor (called every 1 second)
    // ═══════════════════════════════════════════

    /** CPU sample: per-core frequency + usage */
    @JvmStatic external fun nativeSampleCpu(): String

    /** Memory sample: current free/available/cached */
    @JvmStatic external fun nativeSampleMemory(): String

    /** Thermal sample: all zone temperatures */
    @JvmStatic external fun nativeSampleThermal(): String
}
