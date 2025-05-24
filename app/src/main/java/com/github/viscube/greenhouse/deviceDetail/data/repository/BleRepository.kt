package com.github.viscube.greenhouse.deviceDetail.data.repository

import com.github.viscube.greenhouse.deviceDetail.data.bleManager.BleManager
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IBleRepository

class BleRepository(
    private val bleManager: BleManager
) : IBleRepository {

    override fun connectAndListen(address: String, onLineReceived: (String) -> Unit) {
        bleManager.connectAndListen(address, onLineReceived)
    }

    override fun sendCommand(command: String) {
        bleManager.sendCommand(command)
    }

    override fun disconnect() {
        bleManager.disconnect()
    }
}