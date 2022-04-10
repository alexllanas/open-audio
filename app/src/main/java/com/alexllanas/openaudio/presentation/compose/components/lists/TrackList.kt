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

@Composable
fun TrackList(
    stream: List<Track>,
    onTrackClick: (Track) -> Unit = {},
    onHeartClick: (Boolean, Track) -> Unit = { _: Boolean, _: Track -> },
    onMoreClick: (Track) -> Unit = {},
) {
    LazyColumn {
        items(stream) { track ->
            TrackItem(
                track = track,
                onTrackClick = onTrackClick,
                onHeartClick = onHeartClick,
                onMoreClick = onMoreClick
            )
            if (stream.last() == track)
                Spacer(Modifier.height(64.dp))
        }
    }
}