package com.github.viscube.greenhouse.deviceDetail.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceDetailDbEntity(
    @PrimaryKey val connectionData: String,
    val connectionType: String,
    val name: String,
    val wifiSSID: String?,
    val wifiPASS: String?,
    val sensorsJson: String
)