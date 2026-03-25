package com.phonescope.inspector.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phonescope.inspector.data.local.entity.MonitorSampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonitorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSample(sample: MonitorSampleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSamples(samples: List<MonitorSampleEntity>)

    @Query("SELECT * FROM monitor_samples WHERE timestamp >= :since ORDER BY timestamp ASC")
    fun getSamplesSince(since: Long): Flow<List<MonitorSampleEntity>>

    @Query("SELECT * FROM monitor_samples ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentSamples(limit: Int): Flow<List<MonitorSampleEntity>>

    @Query("DELETE FROM monitor_samples WHERE timestamp < :before")
    suspend fun deleteSamplesBefore(before: Long)

    @Query("DELETE FROM monitor_samples")
    suspend fun deleteAllSamples()
}
