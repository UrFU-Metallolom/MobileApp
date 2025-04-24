package com.github.viscube.greenhouse.deviceList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.viscube.greenhouse.deviceList.data.mock.DeviceData
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity
import com.github.viscube.greenhouse.deviceList.presentation.state.ListState


class ListViewModel : ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState

    init {
        mutableState.devices = DeviceData.items // TODO добавить репозиторий
    }

    private class MutableListState : ListState {
        override var devices: List<DeviceEntity> by mutableStateOf(emptyList())
    }
}