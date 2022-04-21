package com.alexllanas.openaudio.presentation.compose.components.lists

import TrackItem
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Track
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.models.TrackUIList
import com.alexllanas.openaudio.presentation.models.TrackUIModel

@Composable
fun TrackList(
    tracks: TrackUIList,
    mainState: MainState,
    onTrackClick: (TrackUIModel) -> Unit = {},
    onHeartClick: (TrackUIModel) -> Unit = { },
    onMoreClick: (TrackUIModel) -> Unit = {},
) {
    LazyColumn {
        items(tracks.tracks) { track ->
            track?.let {
                TrackItem(
                    track = track,
                    onTrackClick = onTrackClick,
                    onHeartClick = onHeartClick,
                    onMoreClick = onMoreClick,
                    mainState = mainState
                )
                if (tracks.tracks.last() == track)
                    Spacer(Modifier.height(144.dp))
            }
        }
    }
}