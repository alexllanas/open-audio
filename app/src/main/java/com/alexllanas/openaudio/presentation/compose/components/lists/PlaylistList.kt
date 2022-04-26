package com.alexllanas.openaudio.presentation.compose.components.lists

import PlaylistItem
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.const
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.util.Constants.Companion.TAG


@Composable
fun PlaylistList(
    playlists: List<Playlist>,
    isCountVisible: Boolean,
    onPlaylistClick: (Playlist) -> Unit,
) {
    if (playlists.isEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("No playlists")
        }
    } else {
        LazyColumn {
            items(playlists) { playlist ->
                Log.d(TAG, "PlaylistList: ${playlist.coverImage}")
                PlaylistItem(
                    playlist = playlist,
                    onPlaylistClick = onPlaylistClick,
                    countVisible = isCountVisible
                )
                if (playlists.last() == playlist) {
                    Spacer(Modifier.height(144.dp))
                }
            }
        }
    }
}