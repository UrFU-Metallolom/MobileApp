package com.github.viscube.greenhouse.deviceList.data.mock

import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity


object DeviceData {
    val items = listOf(
        DeviceEntity(
            name = "Device 1",
            ble = true,
            wifi = true,
            connectionData = "1"
        ),
        DeviceEntity(
            name = "Device 2",
            ble = true,
            wifi = false,
            connectionData = "2",
        ),
        DeviceEntity(
            name = "Device 3",
            ble = false,
            wifi = true,
            connectionData = "3",
        )
    )
}