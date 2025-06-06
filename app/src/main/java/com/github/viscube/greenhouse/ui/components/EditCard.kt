package com.github.viscube.greenhouse.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceDetail.data.mock.DeviceData
import com.github.viscube.greenhouse.deviceDetail.domain.entity.DeviceDetailEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.PresetEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorType

@Composable
fun EditCard(
    device: DeviceDetailEntity,
    presets: List<PresetEntity>,
    onNameChanged: (String) -> Unit = {},
    onWiFiSsidChanged: (String) -> Unit = {},
    onWiFiPasswordChanged: (String) -> Unit = {},
    onTempRefChanged: (String) -> Unit = {},
    onLightRefChanged: (String) -> Unit = {},
    onMoistureRefChanged: (String) -> Unit = {},
    onPresetClicked: (PresetEntity) -> Unit = {}
) {
    Column {
        TextField(
            value = device.name,
            onValueChange = { onNameChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                )
            }
        )
        TextField(
            value = device.wifiSSID ?: "",
            onValueChange = { onWiFiSsidChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.wifi),
                    contentDescription = null,
                )
            }
        )
        TextField(
            value = device.wifiPASS ?: "",
            onValueChange = { onWiFiPasswordChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                )
            }
        )
        LazyColumn {
            items(device.sensors.filter { sensor -> sensor.type != SensorType.WATER }) { sensor ->
                TextField(
                    value = sensor.reference,
                    enabled = sensor.type != SensorType.WATER,
                    onValueChange = {
                        when (sensor.type) {
                            SensorType.LIGHT -> onLightRefChanged(it)
                            SensorType.TEMPERATURE -> onTempRefChanged(it)
                            SensorType.MOISTURE -> onMoistureRefChanged(it)
                            SensorType.WATER -> {}
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.titleMedium,
                    leadingIcon = {
                        Icon(
                            painter = when (sensor.type) {
                                SensorType.LIGHT -> painterResource(R.drawable.lightbulb)
                                SensorType.TEMPERATURE -> painterResource(R.drawable.thermostat)
                                SensorType.MOISTURE -> painterResource(R.drawable.moisture)
                                SensorType.WATER -> painterResource(R.drawable.water)
                            },
                            contentDescription = null,
                        )
                    },
                    prefix = {
                        Row {
                            Text(text = sensor.value)
                            Text(text = stringResource(R.string.slash))
                        }
                    }
                )
                HorizontalDivider()
            }
        }
        LazyColumn {
            items(presets) { preset ->
                PresetItem(preset) { onPresetClicked(preset) }
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditCardPreview() {
    EditCard(
        device = DeviceData.devices.last(),
        presets = listOf(
            PresetEntity("preset1", "20", "40", "60"),
            PresetEntity("preset2", "22", "35", "55")
        )
    )
}