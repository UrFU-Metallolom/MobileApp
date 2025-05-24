package com.github.viscube.greenhouse.deviceList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward
import com.github.viscube.greenhouse.deviceDetail.presentation.screens.DeviceScreen
//import com.github.viscube.greenhouse.deviceList.data.mock.DeviceData
import com.github.viscube.greenhouse.deviceList.domain.IDeviceRepository
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity
import com.github.viscube.greenhouse.deviceList.presentation.screens.ConnectScreen
import com.github.viscube.greenhouse.deviceList.presentation.state.ListState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ListViewModel(
    private val repository: IDeviceRepository,
    private val navigation: StackNavContainer
) : ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState
    val devices = repository.observeAllDevices()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun onConnectClicked(connectionData: String) {
        navigation.forward(DeviceScreen(connectionData))
    }

    fun onAddConnectionClicked() {
        navigation.forward(ConnectScreen())
    }

    fun onLongClicked(device: DeviceEntity) {
        viewModelScope.launch {
            repository.removeDevice(device)
            repository.removeDetailDeviceByKey(device.connectionData)
        }
    }

    private class MutableListState : ListState {
        override var devices: List<DeviceEntity> by mutableStateOf(emptyList())
    }
}