package com.github.viscube.greenhouse.deviceList.presentation.state

import com.github.viscube.greenhouse.deviceList.domain.entity.BLEEntity

/* TODO */
interface ConnectState {
    val bleDevices: List<BLEEntity>
    val deviceCode: String?
}