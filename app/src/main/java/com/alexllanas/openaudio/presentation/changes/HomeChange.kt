package com.alexllanas.openaudio.presentation.changes

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.state.BaseChange

sealed class HomeChange : BaseChange {
    object Loading : HomeChange()
    data class LoginChange(val loggedInUser: User) : HomeChange()
    data class StreamChange(val stream: List<Track>) : HomeChange()
    data class ErrorChange(val throwable: Throwable) : HomeChange()
    data class UserTracksChange(val userTracks: List<Track>) : HomeChange()
    data class SearchChange(
        val searchTrackResults: List<Track>,
        val searchPlaylistResults: List<Playlist>,
        val searchUserResults: List<User>,
        val searchPostResults: List<Track>
    ) : HomeChange()
}