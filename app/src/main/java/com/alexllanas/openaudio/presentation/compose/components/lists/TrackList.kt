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
import com.alexllanas.openaudio.presentation.models.TrackUIList
import com.alexllanas.openaudio.presentation.models.TrackUIModel

@Composable
fun TrackList(
    tracks: TrackUIList,
    onTrackClick: (TrackUIModel) -> Unit = {},
    onHeartClick: (Boolean, TrackUIModel) -> Unit = { _: Boolean, _: TrackUIModel -> },
    onMoreClick: (TrackUIModel) -> Unit = {},
) {
    LazyColumn {
        items(tracks.tracks) { track ->
            TrackItem(
                track = track,
                onTrackClick = onTrackClick,
                onHeartClick = onHeartClick,
                onMoreClick = onMoreClick
            )
            if (tracks.tracks.last() == track)
                Spacer(Modifier.height(144.dp))
        }
    }
}