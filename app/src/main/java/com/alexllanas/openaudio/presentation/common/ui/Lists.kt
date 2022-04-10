package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.home.ui.TrackItem


@Composable
fun PlaylistList(items: List<Playlist>) {
    LazyColumn {
        items(items) { playlist ->
            playlist.name?.let { Text(it) }
        }
    }
}

@Composable
fun UserList(items: List<User>) {
    LazyColumn {
        items(items) { user ->
            user.name?.let { Text(it) }
        }
    }
}


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
                Spacer(Modifier.height(72.dp))
        }
    }
}
