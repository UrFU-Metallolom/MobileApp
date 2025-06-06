package com.github.viscube.greenhouse.deviceDetail.presentation.state

import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.PresetEntity

interface EditState {
    val device: DeviceDetailEntity
    var isSaveCompleted: Boolean
    var presets: List<PresetEntity>
}