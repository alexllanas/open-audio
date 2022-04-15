package com.alexllanas.openaudio.presentation.compose.components.lists

import PlaylistItem
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.util.Constants.Companion.TAG


@Composable
fun PlaylistList(
    playlists: List<Playlist>,
    onPlaylistClick: (Playlist) -> Unit
) {
    LazyColumn {
        items(playlists) { playlist ->
            Log.d(TAG, "PlaylistList: ${playlist.coverImage}")
            PlaylistItem(playlist = playlist, onPlaylistClick = onPlaylistClick)
            if (playlists.last() == playlist) {
                Spacer(Modifier.height(144.dp))
            }
        }
    }
}