package com.alexllanas.openaudio.presentation.compose.components.lists

import TrackItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.presentation.audio.MediaPlayerAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.models.TrackUIList
import com.alexllanas.openaudio.presentation.models.TrackUIModel

@Composable
fun TrackList(
    tracks: TrackUIList,
    mainState: MainState,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    playerViewModel: MediaPlayerViewModel,
    onTrackClick: (TrackUIModel) -> Unit = {},
    onHeartClick: (TrackUIModel) -> Unit = { },
    onMoreClick: (TrackUIModel) -> Unit = {},
) {
    val mediaPlayerState by playerViewModel.mediaPlayerState.collectAsState()
    if (tracks.tracks.isEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("No tracks")
        }
    } else {
        LazyColumn() {
            itemsIndexed(tracks.tracks) { index, track ->
                track?.let {
                    TrackItem(
                        track = track,
                        onTrackClick = onTrackClick,
                        onHeartClick = onHeartClick,
                        onMoreClick = onMoreClick,
                        mainState = mainState,
                        mainViewModel = mainViewModel,
                        playerViewModel = playerViewModel,
                        homeViewModel = homeViewModel,
                        setPlayQueueCallback = {
                            playerViewModel.dispatch(MediaPlayerAction.SetPlayQueue(tracks.toDomain()))
                        }
                    )
                    if (index == tracks.tracks.size - 1
                    ) {
                        if (mediaPlayerState.currentPlayingTrack != null) {
                            Spacer(Modifier.height(144.dp))
                        } else {
                            Spacer(Modifier.height(72.dp))
                        }
                    }
                }
            }
        }
    }
}