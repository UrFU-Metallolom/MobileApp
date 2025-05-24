package com.github.viscube.greenhouse.deviceList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import com.github.viscube.greenhouse.deviceList.domain.IDeviceRepository
import com.github.viscube.greenhouse.deviceList.domain.entity.BLEEntity
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity
import com.github.viscube.greenhouse.deviceList.presentation.state.ConnectState
import com.github.viscube.greenhouse.util.bleScanner.BleScanner
import kotlinx.coroutines.launch


class ConnectViewModel(
    private val bleScanner: BleScanner,
    private val repository: IDeviceRepository,
    private val navigation: StackNavContainer,
) : ViewModel() {
    private val mutableState = MutableConnectState()
    val viewState = mutableState as ConnectState

    fun startScan() {
        bleScanner.startScan { device ->
            if (device !in mutableState.bleDevices) {
                mutableState.bleDevices += device
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        bleScanner.stopScan()
    }

    fun onBackClicked() {
        navigation.back()
    }


    fun onDeviceCodeChanged(code: String) {
        mutableState.deviceCode = code
    }

    fun onCodeConnectClicked() {
        viewModelScope.launch {
            val device = DeviceEntity(
                connectionData = mutableState.deviceCode,
                name = "Device ${mutableState.deviceCode}",
                ble = false,
                wifi = true
            )
            repository.saveDevice(device)
            repository.saveDeviceDetails(device)
            navigation.back()
        }
    }

    fun onBlDeviceClicked(bleEntity: BLEEntity) {
        viewModelScope.launch {
            val device = DeviceEntity(
                connectionData = bleEntity.address,
                name = bleEntity.name,
                ble = true,
                wifi = false
            )

            repository.saveDevice(device)
            repository.saveDeviceDetails(device)
            navigation.back()
        }
    }

}


private class MutableConnectState : ConnectState {
    override var bleDevices: List<BLEEntity> by mutableStateOf(emptyList())
    override var deviceCode: String by mutableStateOf(String())
}