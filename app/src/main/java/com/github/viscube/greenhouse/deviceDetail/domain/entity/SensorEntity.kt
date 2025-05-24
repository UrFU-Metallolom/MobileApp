package com.github.viscube.greenhouse.deviceDetail.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/* TODO */
@Parcelize
data class SensorEntity(
    val type: SensorType,
    var value: String = "",
    var reference: String = "",
) : Parcelable

enum class SensorType {
    LIGHT,
    TEMPERATURE,
    MOISTURE,
    WATER,
}