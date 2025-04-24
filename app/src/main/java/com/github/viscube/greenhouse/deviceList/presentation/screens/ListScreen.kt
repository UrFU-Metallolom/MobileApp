package com.github.viscube.greenhouse.deviceList.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.terrakok.modo.stack.forward
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceDetail.presentation.screens.DeviceScreen
import com.github.viscube.greenhouse.deviceList.presentation.viewModel.ListViewModel
import com.github.viscube.greenhouse.ui.components.DeviceItem
import com.github.viscube.greenhouse.ui.components.IconButton
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class ListScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<ListViewModel> { parametersOf(navigation) }
        val viewState = viewModel.viewState

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.device_list)) },
                    actions = {
                        IconButton(
                            onClick = { navigation.forward(ConnectScreen()) },
                            imageVector = Icons.Rounded.Add
                        )
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(viewState.devices) {
                    DeviceItem(
                        device = it,
                        onClickBLE = { navigation.forward(DeviceScreen(/* TODO */)) },
                        onClickWiFi = { navigation.forward(DeviceScreen(/* TODO */)) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}