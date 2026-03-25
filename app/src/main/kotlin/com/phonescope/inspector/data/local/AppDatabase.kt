package com.phonescope.inspector.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phonescope.inspector.data.local.entity.MonitorSampleEntity
import com.phonescope.inspector.data.local.entity.ScanEntity

@Database(
    entities = [
        ScanEntity::class,
        MonitorSampleEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao
    abstract fun monitorDao(): MonitorDao
}
