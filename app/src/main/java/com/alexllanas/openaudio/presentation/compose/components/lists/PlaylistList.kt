package com.alexllanas.openaudio.presentation.compose.components.lists

import PlaylistItem
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Playlist


@Composable
fun PlaylistList(
    playlists: List<Playlist>,
    onPlaylistClick: (Playlist) -> Unit
) {
    LazyColumn {
        items(playlists) { playlist ->
            PlaylistItem(playlist = playlist, onPlaylistClick = onPlaylistClick)
            if (playlists.last() == playlist) {
                Spacer(Modifier.height(64.dp))
            }
        }
    }
}