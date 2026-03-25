package com.phonescope.inspector.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ═══════════════════════════════════════════
// CPU Models
// ═══════════════════════════════════════════

@Serializable
data class CpuData(
    val global: Map<String, String> = emptyMap(),
    val processors: List<CpuCore> = emptyList(),
    val vulnerabilities: Map<String, String> = emptyMap()
)

@Serializable
data class CpuCore(
    val processor: Int = 0,
    @SerialName("scaling_max_freq") val maxFreq: Long = 0,
    @SerialName("scaling_min_freq") val minFreq: Long = 0,
    @SerialName("scaling_governor") val governor: String = "",
    val caches: List<CacheEntry> = emptyList()
)

@Serializable
data class CacheEntry(
    val level: String = "",
    val type: String = "",
    val size: String = ""
)

@Serializable
data class CpuSample(
    val stat: Map<String, Long> = emptyMap(),
    val frequencies: Map<String, Long> = emptyMap()
)

// ═══════════════════════════════════════════
// Memory Models
// ═══════════════════════════════════════════

@Serializable
data class MemoryData(
    val meminfo: Map<String, Long> = emptyMap()
)

@Serializable
data class MemorySample(
    @SerialName("MemTotal") val memTotal: Long = 0,
    @SerialName("MemFree") val memFree: Long = 0,
    @SerialName("MemAvailable") val memAvailable: Long = 0,
    @SerialName("Cached") val cached: Long = 0,
    @SerialName("SwapTotal") val swapTotal: Long = 0,
    @SerialName("SwapFree") val swapFree: Long = 0
)

// ═══════════════════════════════════════════
// GPU Models
// ═══════════════════════════════════════════

@Serializable
data class GpuData(
    val vendor: String = "Unknown",
    val model: String = "Unknown",
    @SerialName("max_freq") val maxFreq: Long = 0,
    @SerialName("min_freq") val minFreq: Long = 0
)

@Serializable
data class GpuSample(
    @SerialName("busy_percent") val busyPercent: Int = 0,
    @SerialName("current_freq") val currentFreq: Long = 0
)

// ═══════════════════════════════════════════
// Thermal Models
// ═══════════════════════════════════════════

@Serializable
data class ThermalZone(
    val type: String = "",
    val temp: Long = 0
)

@Serializable
data class CoolingDevice(
    val type: String = "",
    @SerialName("max_state") val maxState: Long = 0,
    @SerialName("cur_state") val curState: Long = 0
)

@Serializable
data class ThermalData(
    val zones: List<ThermalZone> = emptyList(),
    @SerialName("cooling_devices") val coolingDevices: List<CoolingDevice> = emptyList()
)

@Serializable
data class ThermalSample(
    val temperatures: List<ThermalZone> = emptyList()
)

// ═══════════════════════════════════════════
// Kernel Models
// ═══════════════════════════════════════════

@Serializable
data class KernelData(
    val sysname: String = "",
    val nodename: String = "",
    val release: String = "",
    val version: String = "",
    val machine: String = "",
    @SerialName("selinux_enforcing") val selinuxEnforcing: Boolean? = null,
    val rooted: Boolean = false
)

// ═══════════════════════════════════════════
// Storage Models
// ═══════════════════════════════════════════

@Serializable
data class StorageData(
    @SerialName("sequential_write_mbps") val writeSpeed: Double = 0.0,
    @SerialName("sequential_read_mbps") val readSpeed: Double = 0.0,
    val tier: String = "Unknown"
)

// ═══════════════════════════════════════════
// AI/NPU Models
// ═══════════════════════════════════════════

@Serializable
data class AiData(
    @SerialName("accelerator_type") val acceleratorType: String = "Unknown",
    @SerialName("accelerator_name") val acceleratorName: String? = null,
    val properties: Map<String, String> = emptyMap()
)

// ═══════════════════════════════════════════
// Process Models
// ═══════════════════════════════════════════

@Serializable
data class ProcessEntry(
    val pid: Int = 0,
    val name: String = "",
    val state: String = "",
    @SerialName("vm_rss_kb") val vmRssKb: Long = 0,
    @SerialName("vm_swap_kb") val vmSwapKb: Long = 0,
    val threads: Int = 0,
    val priority: Int = 0
)
