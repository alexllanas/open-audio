package com.alexllanas.openaudio.presentation.main.state

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User

data class MainState(
    val loggedInUser: User? = null,
    val currentUserPlaylist : Playlist? = null,
    val profileUserPlaylist : Playlist? = null,
    val currentUserPlaylistTracks: List<Track> = emptyList(),
    val profileUserPlaylistTracks: List<Track> = emptyList(),
    val sessionToken: String? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)