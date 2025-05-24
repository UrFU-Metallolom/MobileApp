package com.github.viscube.greenhouse.util.mapper

import com.github.viscube.greenhouse.deviceDetail.domain.entity.ConnectionType
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailDbEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DeviceDetailMapper() {
    private val gson = Gson()

    fun fromDbToEntity(entity: DeviceDetailDbEntity): DeviceDetailEntity {
        val sensorListType = object : TypeToken<List<SensorEntity>>() {}.type
        val sensors: List<SensorEntity> = gson.fromJson(entity.sensorsJson, sensorListType)

        return DeviceDetailEntity(
            connectionData = entity.connectionData,
            connectionType = ConnectionType.valueOf(entity.connectionType),
            name = entity.name,
            wifiSSID = entity.wifiSSID,
            wifiPASS = entity.wifiPASS,
            sensors = sensors
        )
    }

    fun entityToDb(entity: DeviceDetailEntity): DeviceDetailDbEntity {
        val sensorsJson = gson.toJson(entity.sensors)

        return DeviceDetailDbEntity(
            connectionData = entity.connectionData,
            connectionType = entity.connectionType.name,
            name = entity.name,
            wifiSSID = entity.wifiSSID,
            wifiPASS = entity.wifiPASS,
            sensorsJson = sensorsJson
        )
    }
}