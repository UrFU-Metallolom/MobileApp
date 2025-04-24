package com.github.viscube.greenhouse.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.viscube.greenhouse.deviceDetail.data.mock.DeviceData
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceEntity

@Composable
fun DeviceCard(
    device: DeviceEntity,
    // TODO
) {
    Column {
        LazyColumn {
            items(device.sensors) {
                SensorItem(sensor = it)
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    DeviceCard(
        device = DeviceData.devices.first(),
    )
}