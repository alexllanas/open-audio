package com.alexllanas.openaudio.presentation.home.state

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.main.state.BaseState

data class HomeState(
    val loggedInUser: User? = null,
    val stream: List<Track> = emptyList(),
    val userTracks: List<Track> = emptyList(),
    val searchTrackResults: List<Track?> = emptyList(),
    val searchPlaylistResults: List<Playlist?> = emptyList(),
    val searchUserResults: List<User?> = emptyList(),
    val searchPostResults: List<Track?> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
) : BaseState
