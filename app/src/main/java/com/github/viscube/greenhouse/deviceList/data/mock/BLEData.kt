package com.github.viscube.greenhouse.deviceList.data.mock

import com.github.viscube.greenhouse.deviceList.domain.entity.BLEEntity


object BLEData {
    val devices = listOf(
        BLEEntity(
            name="Device 1",
            address = "A5:2E:7B:1F:9C:D0"
        ),
        BLEEntity(
            name="Device 2",
            address = "3D:8A:44:6B:E2:71"
        ),
        BLEEntity(
            name="Device 3",
            address = "F0:9C:25:8E:37:BD"
        )
    )
}