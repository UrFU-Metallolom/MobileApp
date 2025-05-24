package com.github.viscube.greenhouse.deviceList.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceList.presentation.viewModel.ConnectViewModel
import com.github.viscube.greenhouse.ui.components.BLEDeviceItem
import com.github.viscube.greenhouse.ui.components.IconButton
import com.github.viscube.greenhouse.ui.permissions.BluetoothSetupSection
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class ConnectScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<ConnectViewModel> { parametersOf(navigation) }
        val viewState = viewModel.viewState

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.device_add)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { viewModel.onBackClicked() },
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack
                        )
                    }
                )
            },
            bottomBar = {
                TextField(
                    value = viewState.deviceCode ?: "",
                    onValueChange = { viewModel.onDeviceCodeChanged(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.device_code)) },
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.onCodeConnectClicked() },
                            painter = painterResource(R.drawable.wifi)
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {


                BluetoothSetupSection {
                    viewModel.startScan()
                }

                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(viewState.bleDevices) {
                        BLEDeviceItem(
                            device = it,
                            onClick = { viewModel.onBlDeviceClicked(it) },
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}