package com.alexllanas.openaudio.presentation.home.state

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange

sealed class HomeAction {
    data class LoadStreamAction(val sessionToken: String) : HomeAction()
    data class GetUserTracksAction(val userId: String) : HomeAction()
    data class SearchAction(val query: String) : HomeAction()
}


sealed class StreamChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                isLoading = false,
                error = null,
                stream = stream
            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    data class Data(val stream: List<Track>) : StreamChange()
    data class Error(val throwable: Throwable) : StreamChange()
    object Loading : StreamChange()
}

sealed class UserTracksChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                isLoading = false,
                error = null,
                userTracks = userTracks
            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    object Loading : UserTracksChange()
    data class Data(val userTracks: List<Track>) : UserTracksChange()
    data class Error(val throwable: Throwable) : UserTracksChange()
}


sealed class SearchChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                isLoading = false,
                error = null,
                searchTrackResults = searchTrackResults,
                searchPlaylistResults = searchPlaylistResults,
                searchUserResults = searchUserResults,
                searchPostResults = searchPostResults
            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    object Loading : SearchChange()
    data class Data(
        val searchTrackResults: List<Track>,
        val searchPlaylistResults: List<Playlist>,
        val searchUserResults: List<User>,
        val searchPostResults: List<Track>
    ) : SearchChange()

    data class Error(val throwable: Throwable) : SearchChange()
}