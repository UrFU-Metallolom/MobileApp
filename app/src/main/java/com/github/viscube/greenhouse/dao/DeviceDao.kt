package com.github.viscube.greenhouse.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailDbEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.PresetEntity
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Query("SELECT * FROM DeviceEntity")
    suspend fun getAll(): List<DeviceEntity>

    @Query("SELECT * FROM DeviceEntity")
    fun observeAll(): Flow<List<DeviceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceEntity(device: DeviceEntity)

    @Update
    suspend fun updateDeviceEntity(device: DeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDeviceDetailEntity(device: DeviceDetailDbEntity)

    @Query("SELECT * FROM DeviceEntity WHERE connectionData = :data")
    suspend fun getDeviceEntityByData(data: String): DeviceEntity

    @Query("SELECT * FROM DeviceDetailDbEntity WHERE connectionData = :data")
    suspend fun getDeviceDetailEntityByData(data: String): DeviceDetailDbEntity

    @Delete
    suspend fun deleteDeviceEntity(device: DeviceEntity)

    @Query("DELETE FROM devicedetaildbentity WHERE connectionData = :key")
    suspend fun deleteDetailDeviceByKey(key: String)

    @Query("SELECT * FROM PresetEntity")
    suspend fun getPresets(): List<PresetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPresets(presets: List<PresetEntity>)
}