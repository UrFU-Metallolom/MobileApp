package com.github.viscube.greenhouse.deviceDetail.presentation.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.terrakok.modo.stack.back
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.presentation.viewModel.EditViewModel
import com.github.viscube.greenhouse.ui.components.EditCard
import com.github.viscube.greenhouse.ui.components.IconButton
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Parcelize
class EditScreen(
    private val device: DeviceDetailEntity,
    private val onSave: (DeviceDetailEntity) -> Unit,
    override val screenKey: ScreenKey = generateScreenKey(),
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<EditViewModel> { parametersOf(device, navigation) }
        val viewState = viewModel.viewState

        LaunchedEffect(viewState.isSaveCompleted) {
            if (viewState.isSaveCompleted) {
                onSave(viewState.device)
                viewModel.onBackClicked()
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.device_edit)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigation.back() },
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.saveChanges()
                            },
                            imageVector = Icons.Rounded.Done
                        )
                    }
                )
            }
        ) { paddingValues ->
            Row(Modifier.padding(paddingValues)) {
                EditCard(
                    device = viewState.device,
                    presets = viewState.presets,
                    onNameChanged = { value -> viewModel.onNameChanged(value) },
                    onWiFiSsidChanged = { value -> viewModel.onWiFiSsidChanged(value) },
                    onWiFiPasswordChanged = { value -> viewModel.onWiFiPasswordChanged(value) },
                    onTempRefChanged = { value -> viewModel.onTempRefChanged(value) },
                    onLightRefChanged = { value -> viewModel.onLightRefChanged(value) },
                    onMoistureRefChanged = { value -> viewModel.onMoistureRefChanged(value) },
                    onPresetClicked = { value -> viewModel.onPresetClicked(value) }
                )
            }
        }
    }
}