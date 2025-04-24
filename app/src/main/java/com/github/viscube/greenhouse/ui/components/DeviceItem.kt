package com.github.viscube.greenhouse.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceList.data.mock.DeviceData
import com.github.viscube.greenhouse.deviceList.domain.entity.DeviceEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceItem(
    device: DeviceEntity,
    onClickBLE: () -> Unit,
    onClickWiFi: () -> Unit
) {
    TopAppBar(
        title = { Text(text = device.name) },
        actions = {
            if (device.ble) {
                IconButton(
                    onClick = { onClickBLE() },
                    painter = painterResource(R.drawable.bluetooth)
                )
            }
            if (device.wifi) {
                IconButton(
                    onClick = { onClickWiFi() },
                    painter = painterResource(R.drawable.wifi)
                )
            }
        },
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DeviceItemPreview() {
    DeviceItem(
        device = DeviceData.items.first(),
        onClickBLE = {},
        onClickWiFi = {}
    )
}