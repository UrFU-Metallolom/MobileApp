package com.github.viscube.greenhouse.deviceDetail.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import com.github.viscube.greenhouse.deviceDetail.domain.entity.ConnectionType
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorType
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IBleRepository
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IDeviceDetailRepository
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IMqttRepository
import com.github.viscube.greenhouse.deviceDetail.presentation.state.EditState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EditViewModel(
    device: DeviceDetailEntity,
    private val mqttRepository: IMqttRepository,
    private val bleRepository: IBleRepository,
    private val dbRepository: IDeviceDetailRepository,
    private val navigation: StackNavContainer
) : ViewModel() {
    private val mutableState = MutableEditState(device)
    val viewState = mutableState as EditState

    init {
        mutableState.device = device
        connectToDevice()
    }

    fun saveChanges() {
        viewModelScope.launch {
            if (viewState.device.connectionType == ConnectionType.WIFI) {
                for (sensor in viewState.device.sensors) {
                    mqttRepository.publish(mqttTopicsMap[sensor.type] ?: "", sensor.reference)
                }
            } else {
                for (sensor in viewState.device.sensors) {
                    val command = "${bleTopicsMap[sensor.type] ?: ""}=${sensor.reference}\r\n"
                    bleRepository.sendCommand(command)
                    delay(200)
                }
                bleRepository.sendCommand("ssid=${viewState.device.wifiSSID}\r\n")
                delay(200)
                bleRepository.sendCommand("pass=${viewState.device.wifiPASS}\r\n")

            }
            dbRepository.saveDetailDevice(viewState.device)
            dbRepository.updateDeviceEntity(
                viewState.device.connectionData,
                viewState.device.name
            )
            viewState.isSaveCompleted = true
        }
    }

    fun onWiFiSsidChanged(value: String) {
        mutableState.device = mutableState.device.copy(
            wifiSSID = value
        )
    }

    fun onBackClicked() {
        navigation.back()
    }

    fun onWiFiPasswordChanged(value: String) {
        mutableState.device = mutableState.device.copy(
            wifiPASS = value
        )
    }

    fun onTempRefChanged(value: String) {
        updateSensorRef(SensorType.TEMPERATURE, value)
    }

    fun onLightRefChanged(value: String) {
        updateSensorRef(SensorType.LIGHT, value)
    }

    fun onMoistureRefChanged(value: String) {
        updateSensorRef(SensorType.MOISTURE, value)
    }


    fun onNameChanged(value: String) {
        mutableState.device = mutableState.device.copy(
            name = value
        )
    }


    private val mqttTopicsMap = mapOf(
        SensorType.LIGHT to "light/reference",
        SensorType.TEMPERATURE to "temperature/reference",
        SensorType.MOISTURE to "moisture/reference",
    )

    private val bleTopicsMap = mapOf(
        SensorType.LIGHT to "light",
        SensorType.TEMPERATURE to "temperature",
        SensorType.MOISTURE to "moisture",
    )


    private fun connectToDevice() {
        viewModelScope.launch {
            mqttRepository.connect()
        }
    }

    private fun updateSensorRef(type: SensorType, value: String) {
        mutableState.device = mutableState.device.copy(
            sensors = mutableState.device.sensors.map {
                if (it.type == type) it.copy(reference = value) else it
            }
        )
    }

    private class MutableEditState(initialDevice: DeviceDetailEntity) : EditState {
        override var device: DeviceDetailEntity by mutableStateOf(initialDevice)
        override var isSaveCompleted: Boolean by mutableStateOf(false)
    }
}