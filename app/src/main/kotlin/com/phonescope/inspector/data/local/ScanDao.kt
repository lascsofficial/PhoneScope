package com.phonescope.inspector.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phonescope.inspector.data.local.entity.ScanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: ScanEntity): Long

    @Query("SELECT * FROM scans ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<ScanEntity>>

    @Query("SELECT * FROM scans WHERE id = :scanId")
    suspend fun getScanById(scanId: Long): ScanEntity?

    @Query("SELECT * FROM scans ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentScans(limit: Int): Flow<List<ScanEntity>>

    @Query("SELECT COUNT(*) FROM scans")
    suspend fun getScanCount(): Int

    @Query("DELETE FROM scans WHERE id = :scanId")
    suspend fun deleteScan(scanId: Long)

    @Query("DELETE FROM scans")
    suspend fun deleteAllScans()
}
