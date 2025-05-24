package com.github.viscube.greenhouse.deviceList.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceEntity(
    @PrimaryKey(autoGenerate = false) val connectionData: String,
    val name: String,
    val ble: Boolean,
    val wifi: Boolean,
)