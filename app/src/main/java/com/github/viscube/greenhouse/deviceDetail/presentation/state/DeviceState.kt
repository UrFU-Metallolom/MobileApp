package com.github.viscube.greenhouse.deviceDetail.presentation.state

import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity

interface DeviceState {
    val device: DeviceDetailEntity?
}