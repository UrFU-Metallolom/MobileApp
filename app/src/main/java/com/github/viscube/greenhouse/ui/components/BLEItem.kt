package com.github.viscube.greenhouse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceList.data.mock.BLEData
import com.github.viscube.greenhouse.deviceList.domain.entity.BLEEntity
import com.github.viscube.greenhouse.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BLEDeviceItem(
    device: BLEEntity,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Column {
                Text(text = device.name, style = MaterialTheme.typography.titleMedium)
                Text(text = device.address, style = MaterialTheme.typography.bodySmall)
            }
        },
        modifier = Modifier.clickable { onClick() },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.bluetooth),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = Spacing.large)
            )
        },
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun BLEDeviceItemPreview() {
    BLEDeviceItem(
        device = BLEData.devices.first(),
        onClick = {}
    )
}