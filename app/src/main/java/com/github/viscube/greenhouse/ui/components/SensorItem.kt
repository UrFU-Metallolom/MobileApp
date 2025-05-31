package com.github.viscube.greenhouse.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceDetail.data.mock.DeviceData
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorEntity
import com.github.viscube.greenhouse.deviceDetail.domain.entity.SensorType
import com.github.viscube.greenhouse.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorItem(
    sensor: SensorEntity,
) {
    TopAppBar(
        title = {
            Row {
                Text(text = sensor.value.ifEmpty { "?" })
                if (sensor.reference.isNotEmpty()) {
                    Text(text = stringResource(R.string.slash))
                    Text(text = sensor.reference)
                }
            }
        },
        navigationIcon = {
            Icon(
                painter = when (sensor.type) {
                    SensorType.LIGHT -> painterResource(R.drawable.lightbulb)
                    SensorType.TEMPERATURE -> painterResource(R.drawable.thermostat)
                    SensorType.MOISTURE -> painterResource(R.drawable.moisture)
                    SensorType.WATER -> painterResource(R.drawable.water)
                },
                contentDescription = null,
                modifier = Modifier.padding(horizontal = Spacing.large)
            )
        },
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SensorItemPreview() {
    SensorItem(
        sensor = DeviceData.devices.last().sensors.first()
    )
}