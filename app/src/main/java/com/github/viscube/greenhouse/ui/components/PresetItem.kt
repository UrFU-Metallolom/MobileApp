package com.github.viscube.greenhouse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.deviceDetail.domain.entity.PresetEntity
import com.github.viscube.greenhouse.ui.theme.Spacing

@Composable
fun PresetItem(
    preset: PresetEntity,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier.clickable { onClick() },
    ) {

        Row(modifier = Modifier.padding(vertical = Spacing.medium)) {
            Text(
                text = preset.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.small, end = Spacing.medium),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(R.drawable.lightbulb),
                contentDescription = null,
            )
            Text(
                text = preset.light,
                style = MaterialTheme.typography.titleMedium
            )

            Icon(
                painter = painterResource(R.drawable.thermostat),
                contentDescription = null,
            )
            Text(
                text = preset.temperature,
                style = MaterialTheme.typography.titleMedium
            )

            Icon(
                painter = painterResource(R.drawable.moisture),
                contentDescription = null,
            )
            Text(
                text = preset.moisture,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
