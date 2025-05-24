package com.github.viscube.greenhouse.deviceDetail.data.repository

import com.github.viscube.greenhouse.database.Database
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IDeviceDetailRepository
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity
import com.github.viscube.greenhouse.util.mapper.DeviceDetailMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceDetailRepository(
    private val db: Database,
    private val mapper: DeviceDetailMapper
) : IDeviceDetailRepository {

    override suspend fun saveDetailDevice(device: DeviceDetailEntity) =
        withContext(Dispatchers.IO) {
            db.deviceDao().saveDeviceDetailEntity(
                mapper.entityToDb(
                    device
                )
            )
        }

    override suspend fun getDetailDevice(connectionData: String): DeviceDetailEntity =
        withContext(Dispatchers.IO) {
            mapper.fromDbToEntity(
                db.deviceDao().getDeviceDetailEntityByData(connectionData)
            )
        }

    override suspend fun updateDeviceEntity(key: String, newName: String) =
        withContext(Dispatchers.IO) {
            val device: DeviceEntity = db.deviceDao().getDeviceEntityByData(key)
            db.deviceDao().updateDeviceEntity(device.copy(name = newName))

        }
}