package com.github.viscube.greenhouse.deviceDetail.domain.repository

import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity


interface IDeviceDetailRepository {
    suspend fun saveDetailDevice(device: DeviceDetailEntity)

    suspend fun getDetailDevice(connectionData: String): DeviceDetailEntity

    suspend fun updateDeviceEntity(key: String, newName: String)
}