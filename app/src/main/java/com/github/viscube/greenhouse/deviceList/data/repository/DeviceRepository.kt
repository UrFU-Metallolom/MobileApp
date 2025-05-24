package com.github.viscube.greenhouse.deviceList.data.repository

import com.github.viscube.greenhouse.database.Database
import com.github.viscube.greenhouse.deviceDetail.domain.entity.ConnectionType
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorType
import com.github.viscube.greenhouse.deviceList.domain.IDeviceRepository
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity
import com.github.viscube.greenhouse.util.mapper.DeviceDetailMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DeviceRepository(
    private val db: Database,
    private val mapper: DeviceDetailMapper
) : IDeviceRepository {

    override suspend fun getList(): List<DeviceEntity> = withContext(Dispatchers.IO) {
        db.deviceDao().getAll().map {
            DeviceEntity(
                connectionData = it.connectionData,
                name = it.name,
                ble = it.ble,
                wifi = it.wifi,
            )
        }
    }

    override fun observeAllDevices(): Flow<List<DeviceEntity>> = db.deviceDao().observeAll()


    override suspend fun saveDevice(device: DeviceEntity) = withContext(Dispatchers.IO) {
        db.deviceDao().insertDeviceEntity(
            DeviceEntity(
                connectionData = device.connectionData,
                name = device.name,
                ble = device.ble,
                wifi = device.wifi,
            )
        )
    }

    override suspend fun saveDeviceDetails(device: DeviceEntity) = withContext(Dispatchers.IO) {
        db.deviceDao().saveDeviceDetailEntity(
            mapper.entityToDb(
                DeviceDetailEntity(
                    connectionType = if (device.wifi) ConnectionType.WIFI else ConnectionType.BLE,
                    connectionData = device.connectionData,
                    name = device.name,
                    sensors = listOf(
                        SensorEntity(
                            type = SensorType.LIGHT
                        ),
                        SensorEntity(
                            type = SensorType.TEMPERATURE
                        ),
                        SensorEntity(
                            type = SensorType.MOISTURE
                        ),
                        SensorEntity(
                            type = SensorType.WATER,
                        )
                    )
                )
            )
        )
    }

    override suspend fun removeDevice(device: DeviceEntity) = withContext(Dispatchers.IO) {
        db.deviceDao().deleteDeviceEntity(device)
    }

    override suspend fun removeDetailDeviceByKey(key: String) = withContext(Dispatchers.IO) {
        db.deviceDao().deleteDetailDeviceByKey(key)
    }
}