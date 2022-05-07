package com.alexllanas.openaudio.presentation.compose.components

import android.util.Log
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
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.github.krottv.compose.sliders.DefaultThumb
import com.github.krottv.compose.sliders.DefaultTrack
import com.github.krottv.compose.sliders.SliderValueHorizontal
import kotlinx.coroutines.delay
import kotlin.math.floor

@Composable
fun SeekBar(modifier: Modifier = Modifier, mediaPlayerViewModel: MediaPlayerViewModel) {
    var sliderPosition by remember { mutableStateOf(0f) }
    val maxPosition = 100f

    val mediaPlayerState by mediaPlayerViewModel.mediaPlayerState.collectAsState()

    Column(modifier = modifier) {
        SliderValueHorizontal(
            value = mediaPlayerState.currentSecond,
            valueRange = 0F..mediaPlayerState.duration,
            onValueChange = {
                            mediaPlayerState.youTubePlayer?.seekTo(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            thumbHeightMax = true,
            track = { modifier: Modifier,
                      _: Float,
                      interactionSource: MutableInteractionSource,
                      tickFractions: List<Float>,
                      enabled: Boolean ->

                DefaultTrack(
                    modifier = modifier,
                    progress = mediaPlayerState.currentSecond / mediaPlayerState.duration,
//                    progress = fraction,
                    interactionSource = interactionSource,
                    tickFractions = tickFractions,
                    enabled = enabled,
                    height = 2.dp,
                    colorTrack = MaterialTheme.colors.onBackground.copy(alpha = .1f),
                    colorProgress = MaterialTheme.colors.onBackground
                )
            },
            thumb = { modifier: Modifier,
                      offset: Dp,
                      interactionSource: MutableInteractionSource,
                      enabled: Boolean,
                      thumbSize: DpSize ->

                Log.d(TAG, "SeekBar: offset= $offset")
                
                DefaultThumb(
                    modifier = modifier,
                    offset = offset,
                    interactionSource = interactionSource,
                    enabled = enabled,
                    thumbSize = thumbSize,
                    color = MaterialTheme.colors.onBackground,
                    scaleOnPress = 1.3f
                )
            },
            thumbSizeInDp = DpSize(10.dp, 10.dp)
        )

        Row {
            Text(
                text = mediaPlayerState.currentSecond.minuteSecondFormat(),
                modifier = Modifier
                    .weight(1f),
                style = MaterialTheme.typography.caption
            )
            Text(
                text = mediaPlayerState.duration.minuteSecondFormat(),
                modifier = Modifier,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

fun Float.minuteSecondFormat(): String {
    val minutes = floor(this / 60).toInt()
    val seconds = floor(this % 60).toInt()
    return String.format("%d:%02d", minutes, seconds)
}
