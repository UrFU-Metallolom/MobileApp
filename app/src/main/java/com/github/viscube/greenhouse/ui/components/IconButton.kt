package com.github.viscube.greenhouse.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IconButton(
    onClick: () -> Unit,
    imageVector: ImageVector
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    painter: Painter
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            painter = painter,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IconButtonPreview() {
    IconButton(
        onClick = {},
        imageVector = Icons.Default.Done
    )
}