package com.alexllanas.openaudio.presentation.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


val darkColors = darkColors(
//    primary = Color.Gray,
//    onPrimary = Color.White,
//    secondary = Color.Red,
//    surface = Color.DarkGray,
//    onSurface = Color.White,
//    primaryVariant = Color.Gray,
//    onBackground = Color.Gray,
//    background = Color.Black
    primary = Color.White.copy(alpha = 0.7f),
//    primaryVariant = Color(0xFF3700B3),
    primaryVariant = Color(0xFF121212),
    secondary = Color(0xFF03DAC6),
    secondaryVariant = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    error = Color(0xFFCF6679),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,
)


@Composable
fun OpenAudioTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
//    if (isSystemInDarkTheme()) {
    systemUiController.setStatusBarColor(color = Color.Transparent)
    MaterialTheme(colors = darkColors) {
        content()
    }
//    } else {
//
//    }
}