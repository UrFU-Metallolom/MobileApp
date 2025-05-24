package com.github.viscube.greenhouse.deviceDetail.presentation.state

import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity

interface EditState {
    val device: DeviceDetailEntity
    var isSaveCompleted: Boolean
}