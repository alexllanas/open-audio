package com.alexllanas.openaudio.presentation.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.PlayBackButton
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel

@Composable
fun MediaControlPanel(modifier: Modifier = Modifier, mediaPlayerViewModel: MediaPlayerViewModel) {
    val mediaPlayerState by mediaPlayerViewModel.mediaPlayerState.collectAsState()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Icon(
            modifier = Modifier
                .size(42.dp)
                .padding(end = 12.dp),
            imageVector = Icons.Filled.Shuffle,
            contentDescription = stringResource(R.string.previous)
        )
        Icon(
            modifier = Modifier
                .size(48.dp),
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = stringResource(R.string.previous)
        )

        PlayBackButton(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(84.dp),
            isPlayingOrBuffering = mediaPlayerViewModel.isPlaying() || mediaPlayerViewModel.isBuffering()
        ) {
            if (mediaPlayerViewModel.isPlaying()) {
                mediaPlayerState.youTubePlayer?.pause()
//                mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(false))
            } else {
                mediaPlayerState.youTubePlayer?.play()
//                mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(true))
            }
        }
        Icon(
            modifier = Modifier
                .size(48.dp),
            imageVector = Icons.Filled.SkipNext,
            contentDescription = stringResource(R.string.next)
        )
        Icon(
            modifier = Modifier
                .size(42.dp)
                .padding(start = 12.dp),
            imageVector = Icons.Filled.Repeat,
            contentDescription = stringResource(R.string.previous)
        )
    }
}