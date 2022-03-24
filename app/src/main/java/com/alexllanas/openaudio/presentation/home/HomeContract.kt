package com.alexllanas.openaudio.presentation.home

import com.alexllanas.core.domain.models.GeneralError
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User

sealed class HomeAction {
    data class LoginAction(val email: String, val password: String) : HomeAction()
    data class LoadStreamAction(val sessionToken: String) : HomeAction()
    data class GetUserTracksAction(val userId: String) : HomeAction()
    data class SearchAction(val query: String) : HomeAction()
}

internal sealed interface PartialStateChange {
    fun reduce(viewState: HomeViewState): HomeViewState

    sealed class StreamChange : PartialStateChange {
        override fun reduce(viewState: HomeViewState): HomeViewState {
            return when (this) {
                Loading -> viewState.copy(
                    isLoading = true,
                    error = null
                )
                is Data -> viewState.copy(
                    isLoading = false,
                    error = null,
                    stream = stream
                )
                is Error -> viewState.copy(
                    isLoading = false,
                    error = throwable
                )
            }
        }

        object Loading : StreamChange()
        data class Data(val stream: List<Track>) : StreamChange()
        data class Error(val throwable: Throwable) : StreamChange()
    }

    sealed class UserTracksChange : PartialStateChange {
        override fun reduce(viewState: HomeViewState): HomeViewState {
            return when (this) {
                Loading -> viewState.copy(
                    isLoading = true,
                    error = null
                )
                is Data -> viewState.copy(
                    isLoading = false,
                    error = null,
                    userTracks = userTracks
                )
                is Error -> viewState.copy(
                    isLoading = false,
                    error = throwable
                )
            }
        }

        object Loading : UserTracksChange()
        data class Data(val userTracks: List<Track>) : UserTracksChange()
        data class Error(val throwable: Throwable) : UserTracksChange()
    }

    sealed class LoginChange : PartialStateChange {
        override fun reduce(viewState: HomeViewState): HomeViewState {
            return when (this) {
                Loading -> viewState.copy(
                    isLoading = true,
                    error = null
                )
                is Data -> viewState.copy(
                    isLoading = false,
                    error = null,
                    loggedInUser = loggedInUser
                )
                is Error -> viewState.copy(
                    isLoading = false,
                    error = throwable
                )
            }
        }

        object Loading : LoginChange()
        data class Data(val loggedInUser: User) : LoginChange()
        data class Error(val throwable: Throwable) : LoginChange()
    }

    sealed class SearchChange : PartialStateChange {
        override fun reduce(viewState: HomeViewState): HomeViewState {
            return when (this) {
                Loading -> viewState.copy(
                    isLoading = true,
                    error = null
                )
                is Data -> viewState.copy(
                    isLoading = false,
                    error = null,
                    searchTrackResults = searchTrackResults,
                    searchPlaylistResults = searchPlaylistResults,
                    searchUserResults = searchUserResults,
                    searchPostResults = searchPostResults
                )
                is Error -> viewState.copy(
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
}