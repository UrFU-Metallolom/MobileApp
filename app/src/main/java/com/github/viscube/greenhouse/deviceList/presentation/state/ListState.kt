package com.github.viscube.greenhouse.deviceList.presentation.state

import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity

interface ListState {
    val devices: List<DeviceEntity>
}