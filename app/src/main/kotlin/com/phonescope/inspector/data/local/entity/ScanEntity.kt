package com.phonescope.inspector.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Stores a complete device scan result.
 * The full DeviceProfile is serialized to JSON for flexible schema evolution.
 */
@Entity(tableName = "scans")
data class ScanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "device_name")
    val deviceName: String,

    @ColumnInfo(name = "overall_score")
    val overallScore: Int,

    @ColumnInfo(name = "security_score")
    val securityScore: Int,

    @ColumnInfo(name = "profile_json")
    val profileJson: String,
)
