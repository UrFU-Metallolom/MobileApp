package com.github.viscube.greenhouse.deviceDetail.presentation.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.terrakok.modo.stack.back
import com.github.terrakok.modo.stack.forward
import com.github.viscube.greenhouse.deviceDetail.presentation.viewModel.DeviceViewModel
import com.github.viscube.greenhouse.ui.components.DeviceCard
import com.github.viscube.greenhouse.ui.components.IconButton
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class DeviceScreen(
    override val screenKey: ScreenKey = generateScreenKey(),
    // TODO передавать девайс между экранами
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<DeviceViewModel> { parametersOf(navigation) }
        val viewState = viewModel.viewState

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { viewState.device?.let { Text(text = it.name) } },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigation.back() },
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { navigation.forward(EditScreen()) },
                            imageVector = Icons.Rounded.Edit
                        )
                    }
                )
            }
        ) { paddingValues ->
            if (viewState.device == null) return@Scaffold
            Row(Modifier.padding(paddingValues)) {
                DeviceCard(viewState.device!!)
            }
        }
    }
}