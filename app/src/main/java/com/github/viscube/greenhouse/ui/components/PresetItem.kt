package com.github.viscube.greenhouse.ui.components

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
import androidx.compose.ui.tooling.preview.Preview
import com.github.viscube.greenhouse.R
import com.github.viscube.greenhouse.ui.theme.Spacing

@Composable
fun PresetItem(
    // TODO preset entity
    // preset: PresetEntity,
) {
    Column {
        Row(modifier = Modifier.padding(vertical = Spacing.medium)) {
            Text(
                text = "Plant_Name", // TODO plant name
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
            Text(text = "69", style = MaterialTheme.typography.titleMedium) // TODO light preset

            Icon(
                painter = painterResource(R.drawable.thermostat),
                contentDescription = null,
            )
            Text(
                text = "24",
                style = MaterialTheme.typography.titleMedium
            ) // TODO temperature preset

            Icon(
                painter = painterResource(R.drawable.moisture),
                contentDescription = null,
            )
            Text(text = "37", style = MaterialTheme.typography.titleMedium) // TODO moisture preset
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PresetItemPreview() {
    PresetItem(
        // TODO preset mock
        // preset = ...,
    )
}