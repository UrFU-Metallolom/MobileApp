package com.github.viscube.greenhouse.di

import com.github.viscube.greenhouse.deviceDetail.presentation.viewModel.DeviceViewModel
import com.github.viscube.greenhouse.deviceDetail.presentation.viewModel.EditViewModel
import com.github.viscube.greenhouse.deviceList.presentation.viewModel.ConnectViewModel
import com.github.viscube.greenhouse.deviceList.presentation.viewModel.ListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val rootModule = module {
    viewModel { ListViewModel() }
    viewModel { ConnectViewModel() }
    viewModel { DeviceViewModel() }
    viewModel { EditViewModel() }
}