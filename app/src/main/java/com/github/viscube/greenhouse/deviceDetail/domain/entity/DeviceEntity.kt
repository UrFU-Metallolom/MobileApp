package com.github.viscube.greenhouse.deviceDetail.domain.entity

/* TODO */
data class DeviceEntity(
    val connectionType: ConnectionType,
    val connectionData: String,

    val name: String,
    val sensors: List<SensorEntity>,

    val wifiSSID: String? = null,
    val wifiPASS: String? = null
)

enum class ConnectionType {
    BLE,
    WIFI
}