package com.github.viscube.greenhouse.deviceDetail.domain.repository

interface IBleRepository {

    fun connectAndListen(address: String, onLineReceived: (String) -> Unit)

    fun sendCommand(command: String)

    fun disconnect()

}