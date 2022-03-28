package com.alexllanas.openaudio.presentation.home.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.computations.result
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.interactors.home.HomeInteractors
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInteractors: HomeInteractors
) : ViewModel() {

    private val initialState: HomeState by lazy { HomeState() }

    val homeState: StateFlow<HomeState>

    private val _actions = MutableSharedFlow<HomeAction>()
    private val actions = _actions.asSharedFlow()

    init {
        homeState =
            actions
                .toChangeFlow()
                .scan(initialState) { state, change -> change.reduce(state) }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Eagerly,
                    initialState
                )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun SharedFlow<HomeAction>.toChangeFlow(): Flow<PartialStateChange<HomeState>> {
        val executeLoadStream: suspend (String) -> Flow<PartialStateChange<HomeState>> =
            { sessionToken ->
                homeInteractors.getStream(sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { StreamChange.Error(it) },
                            ifRight = { StreamChange.Data(it) }
                        )
                    }.onStart { StreamChange.Loading }
            }
        val executeGetUserTracks: suspend (String) -> Flow<PartialStateChange<HomeState>> =
            { userId ->
                homeInteractors.getUserTracks(userId)
                    .map { result ->
                        result.fold(
                            ifLeft = { UserTracksChange.Error(it) },
                            ifRight = { UserTracksChange.Data(it) }
                        )
                    }.onStart { UserTracksChange.Loading }
            }
        val executeLikeTrack: suspend (String, String, String, String, User) -> Flow<PartialStateChange<HomeState>> =
            { title, mediaUrl, image, sessionToken, loggedInUser ->
                homeInteractors.likeTrack(
                    title,
                    mediaUrl,
                    image,
                    sessionToken,
                    loggedInUser
                )
                    .map { result ->
                        result.fold(
                            ifLeft = { LikeTrackChange.Error(it) },
                            ifRight = {
                                LikeTrackChange.Data(it)
                            }
                        )
                    }.onStart { LikeTrackChange.Loading }
            }
        val executeUnlikeTrack: suspend (Track, String) -> Flow<PartialStateChange<HomeState>> =
            { track, sessionToken ->

                homeInteractors.unlikeTrack(
                    track.id!!,
                    sessionToken
                )
                    .map { result ->
                        result.fold(
                            ifLeft = { UnlikeTrackChange.Error(it) },
                            ifRight = {
                                UnlikeTrackChange.Data(track)
                            }
                        )
                    }.onStart { UnlikeTrackChange.Loading }
            }
        val executeFollowUser: suspend (User, String) -> Flow<PartialStateChange<HomeState>> =
            { user, sessionToken ->
                homeInteractors.followUser(user.id!!, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { FollowUserChange.Error(it) },
                            ifRight = { FollowUserChange.Data(user) }
                        )
                    }.onStart { FollowUserChange.Loading }
            }
        val executeUnfollowUser: suspend (User, String) -> Flow<PartialStateChange<HomeState>> =
            { user, sessionToken ->
                homeInteractors.unfollowUser(user.id!!, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { UnfollowUserChange.Error(it) },
                            ifRight = { UnfollowUserChange.Data(user) }
                        )
                    }.onStart { UnfollowUserChange.Loading }
            }
        val executeGetFollowers: suspend (String, String) -> Flow<PartialStateChange<HomeState>> =
            { userId, sessionToken ->
                homeInteractors.getFollowers(userId, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { GetFollowersChange.Error(it) },
                            ifRight = { GetFollowersChange.Data(it) }
                        )
                    }.onStart { GetFollowersChange.Loading }
            }
        return merge(
            filterIsInstance<HomeAction.GetFollowing>()
                .flatMapConcat { getFollowing(it.userId, it.sessionToken) },
            filterIsInstance<HomeAction.GetFollowers>()
                .flatMapConcat { executeGetFollowers(it.userId, it.sessionToken) },
            filterIsInstance<HomeAction.UnfollowUser>()
                .flatMapConcat { executeUnfollowUser(it.user, it.sessionToken) },
            filterIsInstance<HomeAction.FollowUser>()
                .flatMapConcat { executeFollowUser(it.user, it.sessionToken) },
            filterIsInstance<HomeAction.UnlikeTrack>()
                .flatMapConcat { executeUnlikeTrack(it.track, it.sessionToken) },
            filterIsInstance<HomeAction.LikeTrack>()
                .flatMapConcat {
                    executeLikeTrack(
                        it.title,
                        it.mediaUrl,
                        it.image,
                        it.sessionToken,
                        it.loggedInUser
                    )
                },
            filterIsInstance<HomeAction.LoadStream>()
                .flatMapConcat
                {
                    executeLoadStream(it.sessionToken)
                },
            filterIsInstance<HomeAction.GetUserTracks>()
                .flatMapConcat
                {
                    executeGetUserTracks(it.userId)
                },
            filterIsInstance<HomeAction.SearchAction>()
                .flatMapConcat
                {
                    search(it.query)
                },
        )
    }

    fun dispatch(action: HomeAction) {
        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }


    private suspend fun getFollowing(userId: String, sessionToken: String) =
        homeInteractors.getFollowing(userId, sessionToken)
            .map { result ->
                result.fold(
                    ifLeft = { GetFollowingChange.Error(it) },
                    ifRight = { GetFollowingChange.Data(it) }
                )
            }.onStart { GetFollowingChange.Loading }

    private suspend fun search(query: String) =
        homeInteractors.search(query)
            .map { result ->
                result.fold(
                    ifLeft = { SearchChange.Error(it) },
                    ifRight = {
                        SearchChange.Data(
                            searchTrackResults = it["tracks"] as List<Track>,
                            searchPlaylistResults = it["playlists"] as List<Playlist>,
                            searchUserResults = it["users"] as List<User>,
                            searchPostResults = it["posts"] as List<Track>
                        )
                    }
                )
            }.onStart { SearchChange.Loading }

}