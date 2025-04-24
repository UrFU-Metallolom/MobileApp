package com.github.viscube.greenhouse.deviceDetail.data.mock

import com.github.viscube.greenhouse.deviceDetail.domain.entity.ConnectionType
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorType


object DeviceData {
    val devices = listOf(
        DeviceEntity(
            connectionType = ConnectionType.BLE,
            connectionData = "A5:2E:7B:1F:9C:D0",
            name = "Device 1",
            sensors = listOf(
                SensorEntity(
                    type=SensorType.LIGHT,
                    value = 37,
                    reference = 73
                )
            ),
            wifiSSID = null,
            wifiPASS = null
        ),
        DeviceEntity(
            connectionType = ConnectionType.WIFI,
            connectionData = "7y8hNgyN",
            name = "Device 2",
            sensors = listOf(
                SensorEntity(
                    type=SensorType.MOISTURE,
                    value = 42,
                    reference = 69
                ),
                SensorEntity(
                    type=SensorType.WATER,
                    value = 47
                )
            ),
            wifiSSID = "RTF_IoT",
            wifiPASS = "rtf-1234"
        ),
        DeviceEntity(
            connectionType = ConnectionType.BLE,
            connectionData = "3D:8A:44:6B:E2:71",
            name = "Device 3",
            sensors = listOf(
                SensorEntity(
                    type=SensorType.LIGHT,
                    value = 37,
                    reference = 73
                ),
                SensorEntity(
                    type=SensorType.TEMPERATURE,
                    value = 13,
                    reference = 37
                ),
                SensorEntity(
                    type=SensorType.MOISTURE,
                    value = 42,
                    reference = 69
                ),
                SensorEntity(
                    type=SensorType.WATER,
                    value = 47,
                )
            ),
            wifiSSID = "RTF_IoT",
            wifiPASS = "rtf-1234"
        )
    )
}