package com.github.viscube.greenhouse.deviceDetail.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.viscube.greenhouse.deviceDetail.data.mock.DeviceData
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceEntity
import com.github.viscube.greenhouse.deviceDetail.presentation.state.EditState


class EditViewModel : ViewModel() {
    private val mutableState = MutableEditState()
    val viewState = mutableState as EditState

    init {
        mutableState.device = DeviceData.devices.last() // TODO добавить репозиторий
    }

    private class MutableEditState : EditState {
        override var device: DeviceEntity? by mutableStateOf(null) // TODO убрать null?
    }
}