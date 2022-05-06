package com.alexllanas.openaudio.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexllanas.openaudio.R

@Composable
fun PlayBackButton(modifier: Modifier = Modifier, isPlaying: Boolean, onClick: () -> Unit ) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            },
        imageVector = if (isPlaying)
            Icons.Filled.PauseCircle
        else
            Icons.Filled.PlayCircle,
        contentDescription = stringResource(R.string.play_pause_button)
    )
}