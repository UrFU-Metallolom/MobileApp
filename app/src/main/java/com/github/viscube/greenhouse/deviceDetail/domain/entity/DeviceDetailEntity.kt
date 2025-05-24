package com.github.viscube.greenhouse.deviceDetail.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DeviceDetailEntity(
    val connectionData: String,
    val connectionType: ConnectionType,
    val name: String,
    val sensors: List<SensorEntity>,
    var wifiSSID: String? = null,
    var wifiPASS: String? = null
) : Parcelable

enum class ConnectionType {
    BLE,
    WIFI
}