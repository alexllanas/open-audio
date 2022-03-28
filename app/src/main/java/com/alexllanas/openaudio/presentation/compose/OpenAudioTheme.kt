package com.alexllanas.openaudio.presentation.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val lightColors = lightColors(
    primary = Color(0xFFE30425),
    secondary = Color(0xFFE30425),
    surface = Color(0xFF4E0D3A),
    onSurface = Color.White,
    primaryVariant = Color(0xFF720D5D)
)

@Composable
fun OpenAudioTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = lightColors) {
        content()
    }
}