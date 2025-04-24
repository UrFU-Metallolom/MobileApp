package com.github.viscube.greenhouse.deviceList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.viscube.greenhouse.deviceList.data.mock.BLEData
import com.github.viscube.greenhouse.deviceList.domain.entity.BLEEntity
import com.github.viscube.greenhouse.deviceList.presentation.state.ConnectState


class ConnectViewModel : ViewModel() {
    private val mutableState = MutableConnectState()
    val viewState = mutableState as ConnectState

    init {
        mutableState.bleDevices = BLEData.devices // TODO добавить репозиторий
    }

    private class MutableConnectState : ConnectState {
        override var bleDevices: List<BLEEntity> by mutableStateOf(emptyList())
        override var deviceCode: String? by mutableStateOf(null)
    }
}