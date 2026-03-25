package com.phonescope.inspector.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Stores 1-minute aggregated monitor samples from the flight recorder.
 * Values are averages over 60 one-second samples.
 */
@Entity(tableName = "monitor_samples")
data class MonitorSampleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "cpu_usage_percent")
    val cpuUsagePercent: Float,

    @ColumnInfo(name = "cpu_freq_avg_mhz")
    val cpuFreqAvgMhz: Int,

    @ColumnInfo(name = "ram_used_mb")
    val ramUsedMb: Long,

    @ColumnInfo(name = "ram_available_mb")
    val ramAvailableMb: Long,

    @ColumnInfo(name = "gpu_busy_percent")
    val gpuBusyPercent: Float,

    @ColumnInfo(name = "battery_percent")
    val batteryPercent: Int,

    @ColumnInfo(name = "battery_current_ma")
    val batteryCurrentMa: Int,

    @ColumnInfo(name = "battery_temp_c")
    val batteryTempC: Float,

    @ColumnInfo(name = "max_thermal_temp_c")
    val maxThermalTempC: Float,
)
