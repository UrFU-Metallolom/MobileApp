package com.github.viscube.greenhouse.deviceDetail.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import com.github.terrakok.modo.stack.forward
import com.github.viscube.greenhouse.deviceDetail.domain.entity.ConnectionType
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorType
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IBleRepository
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IDeviceDetailRepository
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IMqttRepository
import com.github.viscube.greenhouse.deviceDetail.presentation.screens.EditScreen
import com.github.viscube.greenhouse.deviceDetail.presentation.state.DeviceState
import kotlinx.coroutines.launch

class DeviceViewModel(
    private val bleRepository: IBleRepository,
    private val mqttRepository: IMqttRepository,
    private val bdRepository: IDeviceDetailRepository,
    private val navigation: StackNavContainer,
    private val connectionData: String
) : ViewModel() {

    private val mutableState = MutableDeviceState()
    val viewState = mutableState as DeviceState

    private val bleSensorTypeMap = mapOf(
        "temperature" to SensorType.TEMPERATURE,
        "light" to SensorType.LIGHT,
        "moisture" to SensorType.MOISTURE,
        "water" to SensorType.WATER
    )

    private val bleReferenceMap = mapOf(
        "temperature_ref" to SensorType.TEMPERATURE,
        "light_ref" to SensorType.LIGHT,
        "moisture_ref" to SensorType.MOISTURE
    )

    private val mqttTopicsMap = mapOf(
        "light/value" to SensorType.LIGHT,
        "temperature/value" to SensorType.TEMPERATURE,
        "moisture/value" to SensorType.MOISTURE,
        "water/value" to SensorType.WATER
    )

    init {
        viewModelScope.launch {
            mutableState.device = bdRepository.getDetailDevice(connectionData)
            connectToDevice()
        }
    }

    fun onBackClicked() {
        navigation.back()
    }

    fun onEditClicked() {
        navigation.forward(
            EditScreen(
                viewState.device!!, onSave = { updatedDevice ->
                    updateDevice(updatedDevice)
                }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        bleRepository.disconnect()
    }

    fun updateDevice(updated: DeviceDetailEntity) {
        mutableState.device = updated
    }


    private fun connectToDevice() {
        viewModelScope.launch {
            if (viewState.device!!.connectionType == ConnectionType.BLE) {
                bleRepository.connectAndListen(mutableState.device!!.connectionData) { line ->
                    val (key, value) = line.split("=", limit = 2).map { it.trim() }
                    when (key) {
                        in bleSensorTypeMap -> updateSensorValue(bleSensorTypeMap[key]!!, value)
                        in bleReferenceMap -> updateSensorReference(bleReferenceMap[key]!!, value)
                    }
                }
            } else {
                mqttRepository.connect()
                mqttRepository.subscribe(mqttTopicsMap.keys.toList())
                mqttTopicsMap.keys.forEach { topic ->
                    launch {
                        mqttRepository.observeTopic(topic).collect { value ->
                            updateSensorValue(
                                type = mqttTopicsMap[topic] ?: return@collect,
                                newValue = value
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateSensorValue(type: SensorType, newValue: String) {
        mutableState.device = mutableState.device?.copy(
            sensors = mutableState.device?.sensors?.map {
                if (it.type == type) it.copy(value = newValue) else it
            } ?: emptyList()
        )
    }

    private fun updateSensorReference(type: SensorType, newValue: String) {
        mutableState.device = mutableState.device?.copy(
            sensors = mutableState.device?.sensors?.map {
                if (it.type == type) it.copy(reference = newValue) else it
            } ?: emptyList()
        )
    }

    private class MutableDeviceState : DeviceState {
        override var device: DeviceDetailEntity? by mutableStateOf(null)
    }
}