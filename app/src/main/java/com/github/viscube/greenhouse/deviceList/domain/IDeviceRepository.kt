package com.github.viscube.greenhouse.deviceList.domain

import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity
import kotlinx.coroutines.flow.Flow

interface IDeviceRepository {
    suspend fun getList(): List<DeviceEntity>
    suspend fun saveDevice(device: DeviceEntity)
    suspend fun saveDeviceDetails(device: DeviceEntity)
    suspend fun removeDevice(device: DeviceEntity)
    suspend fun removeDetailDeviceByKey(key: String)
    fun observeAllDevices(): Flow<List<DeviceEntity>>


}