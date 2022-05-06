package com.alexllanas.openaudio.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.github.krottv.compose.sliders.DefaultThumb
import com.github.krottv.compose.sliders.DefaultTrack
import com.github.krottv.compose.sliders.SliderValueHorizontal

@Composable
fun SeekBar(modifier: Modifier = Modifier) {
    var sliderPosition by remember { mutableStateOf(0f) }
    val maxPosition = 100f

    Column(modifier = modifier) {
        SliderValueHorizontal(
            sliderPosition, { sliderPosition = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            thumbHeightMax = true,
            track = { modifier: Modifier,
                      fraction: Float,
                      interactionSource: MutableInteractionSource,
                      tickFractions: List<Float>,
                      enabled: Boolean ->

                DefaultTrack(
                    modifier,
                    fraction,
                    interactionSource,
                    tickFractions,
                    enabled,
                    height = 4.dp,
                    colorTrack = MaterialTheme.colors.onBackground.copy(alpha = .1f),
                    colorProgress = MaterialTheme.colors.onBackground
                )
            },
            thumb = { modifier: Modifier,
                      offset: Dp,
                      interactionSource: MutableInteractionSource,
                      enabled: Boolean,
                      thumbSize: DpSize ->

                DefaultThumb(
                    modifier, offset, interactionSource, enabled, thumbSize,
                    color = MaterialTheme.colors.onBackground,
                    scaleOnPress = 1.3f
                )
            },
            thumbSizeInDp = DpSize(10.dp, 10.dp)
        )

        Row {
            Text(
                text = "$sliderPosition",
                modifier = Modifier
                    .weight(1f),
                style = MaterialTheme.typography.caption
            )
            Text(
                text = "$maxPosition",
                modifier = Modifier,
                style = MaterialTheme.typography.caption
            )
        }
    }
}
