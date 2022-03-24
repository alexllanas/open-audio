package com.alexllanas.openaudio.presentation.home

import com.alexllanas.core.domain.models.GeneralError
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User

data class HomeViewState(
    val loggedInUser: User? = null,
    val stream: List<Track> = emptyList(),
    val userTracks: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val searchTrackResults: List<Track> = emptyList(),
    val searchPlaylistResults: List<Playlist> = emptyList(),
    val searchUserResults: List<User> = emptyList(),
    val searchPostResults: List<Track> = emptyList()
)
