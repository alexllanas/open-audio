package com.alexllanas.openaudio.presentation.util

import androidx.compose.ui.graphics.Color

class ColorUtil {
    object HexToJetpackColor {
        fun getColor(colorString: String): Color {
            return Color(android.graphics.Color.parseColor("#$colorString"))
        }
    }
}