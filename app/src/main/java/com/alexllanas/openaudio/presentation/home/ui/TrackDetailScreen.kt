package com.alexllanas.openaudio.presentation.home.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel

@Composable
fun TrackDetailScreen(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    mediaPlayerViewModel: MediaPlayerViewModel
) {
    val mainState by mainViewModel.mainState.collectAsState()
    val mediaPlayerState by mediaPlayerViewModel.mediaPlayerState.collectAsState()

    Scaffold {

        mediaPlayerState.currentPlayingTrack?.let { track ->
            Text("${track.title}")
        }
    }
}