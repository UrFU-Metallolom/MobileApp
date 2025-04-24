package com.github.viscube.greenhouse.deviceDetail.domain.entity

/* TODO */
data class SensorEntity(
    val type: SensorType,
    val value: Int,
    val reference: Int? = null,
)

enum class SensorType {
    LIGHT,
    TEMPERATURE,
    MOISTURE,
    WATER
}