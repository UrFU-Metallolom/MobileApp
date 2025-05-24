package com.github.viscube.greenhouse.di

import com.github.viscube.greenhouse.BuildConfig
import com.github.viscube.greenhouse.deviceDetail.data.bleManager.BleManager
import com.github.viscube.greenhouse.deviceDetail.data.repository.BleRepository
import com.github.viscube.greenhouse.deviceDetail.data.repository.DeviceDetailRepository
import com.github.viscube.greenhouse.deviceDetail.data.repository.MqttConfig
import com.github.viscube.greenhouse.deviceDetail.data.repository.MqttRepository
import com.github.viscube.greenhouse.deviceDetail.data.repository.MqttService
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IBleRepository
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IDeviceDetailRepository
import com.github.viscube.greenhouse.deviceDetail.domain.repository.IMqttRepository
import com.github.viscube.greenhouse.deviceDetail.presentation.viewModel.DeviceViewModel
import com.github.viscube.greenhouse.deviceDetail.presentation.viewModel.EditViewModel
import com.github.viscube.greenhouse.deviceList.data.repository.DeviceRepository
import com.github.viscube.greenhouse.deviceList.domain.IDeviceRepository
import com.github.viscube.greenhouse.deviceList.presentation.viewModel.ConnectViewModel
import com.github.viscube.greenhouse.deviceList.presentation.viewModel.ListViewModel
import com.github.viscube.greenhouse.util.bleScanner.BleScanner
import com.github.viscube.greenhouse.util.mapper.DeviceDetailMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {

    single {
        MqttConfig(
            serverUri = "tcp://m8.wqtt.ru:19964",
            username = BuildConfig.MQTT_USER_NAME,
            password = BuildConfig.MQTT_PASSWORD
        )
    }

    single { MqttService(androidContext(), get()) }

    single<IMqttRepository> { MqttRepository(get()) }

    single<IDeviceRepository> {
        DeviceRepository(get(), get())
    }

    single<IDeviceDetailRepository> {
        DeviceDetailRepository(get(), get())
    }

    single<IBleRepository> {
        BleRepository(get())
    }

    single { BleScanner(get()) }

    single { BleManager(get()) }

    single { DeviceDetailMapper() }

    viewModel { ListViewModel(get(), it.get()) }
    viewModel { ConnectViewModel(get(), get(), it.get()) }
    viewModel { DeviceViewModel(get(), get(), get(), it.get(), it.get()) }
    viewModel { EditViewModel(it.get(), get(), get(), get(), it.get()) }
}