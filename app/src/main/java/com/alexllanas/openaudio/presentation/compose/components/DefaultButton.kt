package com.alexllanas.openaudio.presentation.compose.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DefaultButton(@StringRes stringResource: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background
        ),
        border = BorderStroke(1.dp, Color.Gray),
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        Text(
            stringResource(stringResource),
            color = MaterialTheme.colors.onSurface,
        )
    }
}