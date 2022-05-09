package com.alexllanas.openaudio.presentation.compose.components.lists

import PlaylistItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerState


@Composable
fun PlaylistList(
    playlists: List<Playlist>,
    isCountVisible: Boolean,
    mediaPlayerState: MediaPlayerState,
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
            itemsIndexed(playlists) { index, playlist ->
                PlaylistItem(
                    playlist = playlist,
                    onPlaylistClick = onPlaylistClick,
                    countVisible = isCountVisible
                )
                if (index == playlists.size - 1) {
                    if (mediaPlayerState.currentPlayingTrack != null) {
                        Spacer(Modifier.height(144.dp))
                    } else {
                        Spacer(Modifier.height(72.dp))
                    }                }
            }
        }
    }
}