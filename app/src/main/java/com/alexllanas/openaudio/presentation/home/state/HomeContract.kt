package com.alexllanas.openaudio.presentation.home.state

import android.util.Log
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Post
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.common.state.Action
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange

sealed class HomeAction : Action() {

    object ClearHomeState : HomeAction()
    data class LoadStream(val userId: String, val sessionToken: String) : HomeAction()
    data class GetUserTracks(val userId: String) : HomeAction()
    data class SearchAction(val query: String) : HomeAction()
    data class LikeTrack(
        val track: Track,
        val sessionToken: String,
        val loggedInUser: User
    ) : HomeAction()

    data class UnlikeTrack(
        val track: Track,
        val sessionToken: String
    ) : HomeAction()

    data class ToggleLikeSearchTracks(
        val trackId: String,
        val sessionToken: String
    ) : HomeAction()

    data class ToggleLikeStreamTrack(
        val trackId: String,
        val sessionToken: String,
    ) : HomeAction()

    data class FollowUser(
        val user: User,
        val sessionToken: String
    ) : HomeAction()

    data class UnfollowUser(
        val user: User,
        val sessionToken: String
    ) : HomeAction()

    data class AddTrackToPlaylist(
        val track: Track,
        val playlistName: String,
        val playListId: String,
        val sessionToken: String
    ) : HomeAction()

    data class GetFollowers(
        val userId: String,
        val sessionToken: String
    ) : HomeAction()

    data class GetFollowing(
        val userId: String,
        val sessionToken: String
    ) : HomeAction()

    data class SelectTab(val tabIndex: Int) : HomeAction()
    data class SelectTrack(val selectedTrack: Track) : HomeAction()
    data class SelectPlaylist(val selectedPlaylist: Playlist?) : HomeAction()
    data class GetPlaylistTracks(val playlistUrl: String) : HomeAction()
    data class QueryTextChanged(val query: String) : HomeAction()
    data class GetUser(val id: String, val sessionToken: String) : HomeAction()
    data class ToggleTrackOptionsLike(val trackId: String, val sessionToken: String) : HomeAction()
    data class TogglePlaylistTrackLike(val trackId: String, val sessionToken: String) : HomeAction()
    data class CreatePlaylist(
        val playlistName: String = "",
        val user: User,
        val sessionToken: String
    ) :
        HomeAction()

}

class ClearHomeStateChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return HomeState()
    }
}
sealed class CreatePlaylistChange : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return when (this) {
            is Data -> {
                state.loggedInUser?.let { user ->
                    state.copy(
                        loggedInUser = state.loggedInUser.copy(
                            playlists = user.playlists.plus(
                                playlist
                            )
                        ),
                        isLoading = false,
                        error = null,
                    )
                } ?: state
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val playlist: Playlist) : CreatePlaylistChange()
    data class Error(val throwable: Throwable) : CreatePlaylistChange()
    object Loading : CreatePlaylistChange()
}


sealed class TogglePlaylistTrackLikeChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    selectedPlaylistTracks = updateTrackList(
                        post,
                        state.selectedPlaylistTracks,
                        trackId
                    ),
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val post: Post, val trackId: String) : TogglePlaylistTrackLikeChange()
    data class Error(val throwable: Throwable) : TogglePlaylistTrackLikeChange()
    object Loading : TogglePlaylistTrackLikeChange()
}

sealed class ToggleTrackOptionsLikeChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    selectedTrack = state.selectedTrack?.copy(liked = post.loved),
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val post: Post, val trackId: String) : ToggleTrackOptionsLikeChange()
    data class Error(val throwable: Throwable) : ToggleTrackOptionsLikeChange()
    object Loading : ToggleTrackOptionsLikeChange()
}

sealed class SelectTrackChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> state.copy(
                selectedTrack = selectedTrack
            )
        }
    }

    data class Data(val selectedTrack: Track) : SelectTrackChange()
}

sealed class ToggleLikeStreamTrackChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    stream = updateTrackList(post, state.stream, trackId),
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val post: Post, val trackId: String) : ToggleLikeStreamTrackChange()
    data class Error(val throwable: Throwable) : ToggleLikeStreamTrackChange()
    object Loading : ToggleLikeStreamTrackChange()
}

sealed class ToggleLikeSearchTrackChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    searchTrackResults = updateTrackList(post, state.searchTrackResults, trackId),
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val post: Post, val trackId: String) : ToggleLikeSearchTrackChange()
    data class Error(val throwable: Throwable) : ToggleLikeSearchTrackChange()
    object Loading : ToggleLikeSearchTrackChange()
}

sealed class GetUserChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    selectedUser = user,
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val user: User) : GetUserChange()
    data class Error(val throwable: Throwable) : GetUserChange()
    object Loading : GetUserChange()
}

sealed class SelectTabChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    searchScreenState = state.searchScreenState?.copy(currentTab = tabIndex),
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val tabIndex: Int) : SelectTabChange()
    data class Error(val throwable: Throwable) : SelectTabChange()
    object Loading : SelectTabChange()
}

sealed class SelectPlaylistChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    selectedPlaylist = playlist,
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val playlist: Playlist) : SelectPlaylistChange()
    data class Error(val throwable: Throwable) : SelectPlaylistChange()
    object Loading : SelectPlaylistChange()
}

sealed class GetPlaylistTracksChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    selectedPlaylistTracks = tracks,
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val tracks: List<Track>) : GetPlaylistTracksChange()
    data class Error(val throwable: Throwable) : GetPlaylistTracksChange()
    object Loading : GetPlaylistTracksChange()
}

sealed class GetFollowersChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    selectedUserFollowers = followers,
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val followers: List<User>) : GetFollowersChange()
    data class Error(val throwable: Throwable) : GetFollowersChange()
    object Loading : GetFollowersChange()
}

sealed class TextChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is QueryTextChange -> state.copy(
                searchScreenState = state.searchScreenState?.copy(query = query)
            )
        }
    }

    data class QueryTextChange(val query: String) : TextChange()
}

sealed class GetFollowingChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> {
                state.copy(
                    selectedUserFollowing = following,
                    isLoading = false,
                    error = null,
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val following: List<User>) : GetFollowingChange()
    data class Error(val throwable: Throwable) : GetFollowingChange()
    object Loading : GetFollowingChange()
}

sealed class AddTrackToPlaylistChange : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return when (this) {
            is Data -> {
                // TODO("refresh logged in user to update playlist")
                Log.d(TAG, "reduce: Track has been added to playlist.")
                state.copy(

                    isLoading = false,
                    error = null
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val track: Track) : AddTrackToPlaylistChange()
    data class Error(val throwable: Throwable) : AddTrackToPlaylistChange()
    object Loading : AddTrackToPlaylistChange()
}

sealed class FollowUserChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> user.updateFollowUser(state, true)
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )

        }
    }

    data class Data(val user: User) : FollowUserChange()
    data class Error(val throwable: Throwable) : FollowUserChange()
    object Loading : FollowUserChange()
}

sealed class UnfollowUserChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> user.updateFollowUser(state, false)
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val user: User) : UnfollowUserChange()
    data class Error(val throwable: Throwable) : UnfollowUserChange()
    object Loading : UnfollowUserChange()
}

sealed class LikeTrackChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Data -> track.updateLikeTrack(state, true)
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Data(val track: Track) : LikeTrackChange()
    data class Error(val throwable: Throwable) : LikeTrackChange()
    object Loading : LikeTrackChange()
}

sealed class UnlikeTrackChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> {
                track.updateLikeTrack(state, false)
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    data class Data(val track: Track) : UnlikeTrackChange()
    data class Error(val throwable: Throwable) : UnlikeTrackChange()
    object Loading : UnlikeTrackChange()
}

sealed class StreamChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                isLoading = false,
                error = null,
                stream = setLikedTracks(stream, userId)

            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }


    data class Data(val userId: String, val stream: List<Track>) : StreamChange()
    data class Error(val throwable: Throwable) : StreamChange()
    object Loading : StreamChange()
}

sealed class UserTracksChange : PartialStateChange<HomeState> {
    override fun reduce(state: HomeState): HomeState {
        return when (this) {
            is Loading -> state.copy(
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
            is Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                isLoading = false,
                error = null,
                searchTrackResults = setLikedTracks(searchTrackResults, "621039ed3cb9c73d9c963ae6"),
                searchPlaylistResults = searchPlaylistResults,
                searchUserResults = searchUserResults,
                searchPostResults = setLikedTracks(searchPostResults, "621039ed3cb9c73d9c963ae6"),
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

private fun Track.updateLikeTrack(
    state: HomeState,
    isLiked: Boolean
): HomeState {
    Log.d(TAG, "updateLikeTrack: Track Id: $id")
    val updateSearchTracks = arrayListOf<Track>()
    val updateStream = arrayListOf<Track>()
    if (state.searchTrackResults.find { it.id == id } != null) {
        state.searchTrackResults.map {
            if (it.id == id) {
                updateSearchTracks.add(it.copy(liked = isLiked))
            } else {
                updateSearchTracks.add(it.copy())
            }
        }
    } else if (state.stream.find {
            it.id == id
        } != null) {
        state.stream.map {
            if (it.id == id) {
                updateStream.add(it.copy(liked = isLiked))
            } else {
                updateStream.add(it.copy())
            }
        }
    } else {
        state.searchTrackResults.forEach {
            updateSearchTracks.add(it.copy())
        }
        state.stream.forEach {
            updateStream.add(it.copy())
        }
    }
    return state.copy(
        isLoading = false,
        error = null,
        stream = updateStream,
        searchTrackResults = updateSearchTracks
    )
}

private fun User.updateFollowUser(
    state: HomeState,
    shouldFollow: Boolean
): HomeState {
    val updateUsers = arrayListOf<User>()
    if (state.searchUserResults.find { it.id == id } != null) {
        state.searchUserResults.map {
            if (it.id == id) {
                updateUsers.add(it.copy(isSubscribing = shouldFollow))
            } else {
                updateUsers.add(it.copy())
            }
        }
    } else {
        state.searchUserResults.forEach {
            updateUsers.add(it.copy())
        }
    }
    return state.copy(
        searchUserResults = updateUsers,
        isLoading = false,
        error = null
    )
}

//private fun updateTrackLike(post: Post, track: Track): Track {
//}

private fun updateTrackList(post: Post, tracks: List<Track>, trackId: String): List<Track> {
    val updateTracks = arrayListOf<Track>()
    tracks.forEach { track ->
        if (track.id == trackId) {
            post.track?.let {
                updateTracks.add(
                    track.copy(
                        liked = post.loved,
                    )
                )
            } ?: updateTracks.add(track.copy(liked = false))
        } else {
            updateTracks.add(track.copy())
        }

    }
    return updateTracks
}

private fun setLikedTracks(stream: List<Track>, userId: String): List<Track> {
    val update = arrayListOf<Track>()
    stream.onEach { track ->
        if (track.userLikeIds.contains(userId)) {
            update.add(track.copy(liked = true))
        } else {
            update.add(track.copy())
        }
    }
    return update
}