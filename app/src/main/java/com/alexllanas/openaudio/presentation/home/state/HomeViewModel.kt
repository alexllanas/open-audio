package com.alexllanas.openaudio.presentation.home.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.domain.models.canLike
import com.alexllanas.core.interactors.home.HomeInteractors
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.IllegalArgumentException
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

    fun dispatch(action: HomeAction) {
        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch(Dispatchers.IO) {
            _actions.emit(action)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun SharedFlow<HomeAction>.toChangeFlow(): Flow<PartialStateChange<HomeState>> {

        val executeToggleLike: suspend (String, String) -> Flow<PartialStateChange<HomeState>> =
            { trackId, sessionToken ->
                homeInteractors.toggleLike(trackId, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ToggleLikeSearchTrackChange.Error(it) },
                            ifRight = {
                                ToggleLikeSearchTrackChange.Data(it, trackId)
                            }
                        )
                    }.onStart { ToggleLikeSearchTrackChange.Loading }
            }
        val executeToggleLikeStreamTrack: suspend (String, String) -> Flow<PartialStateChange<HomeState>> =
            { trackId, sessionToken ->
                homeInteractors.toggleLike(trackId, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ToggleLikeStreamTrackChange.Error(it) },
                            ifRight = {
                                ToggleLikeStreamTrackChange.Data(it, trackId)
                            }
                        )
                    }.onStart { ToggleLikeStreamTrackChange.Loading }
            }
        val executeLoadStream: suspend (String) -> Flow<PartialStateChange<HomeState>> =
            { sessionToken ->
                homeInteractors.getStream(sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { StreamChange.Error(it) },
                            ifRight = {
                                StreamChange.Data(it)
                            }
                        )
                    }.onStart { StreamChange.Loading }
            }
        val executeGetUser: suspend (String, String) -> Flow<PartialStateChange<HomeState>> =
            { userId, sessionToken ->
//                delay(300)
                homeInteractors.getUser(userId, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { GetUserChange.Error(it) },
                            ifRight = { GetUserChange.Data(it) }
                        )
                    }.onStart { GetUserChange.Loading }
            }
        val executeGetUserTracks: suspend (String) -> Flow<PartialStateChange<HomeState>> =
            { userId ->
//                delay(300)
                homeInteractors.getUserTracks(userId)
                    .map { result ->
                        result.fold(
                            ifLeft = { UserTracksChange.Error(it) },
                            ifRight = { UserTracksChange.Data(it) }
                        )
                    }.onStart { UserTracksChange.Loading }
            }
        val executeLikeTrack: suspend (Track, String, User) -> Flow<PartialStateChange<HomeState>> =
            { track, sessionToken, loggedInUser ->
                homeInteractors.likeTrack(
                    track,
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
        val executeGetPlaylistTracks: suspend (String) -> Flow<PartialStateChange<HomeState>> =
            { playlistUrl ->
                homeInteractors.getPlaylistTracks(playlistUrl)
                    .map { result ->
                        result.fold(
                            ifLeft = { GetPlaylistTracksChange.Error(it) },
                            ifRight = { GetPlaylistTracksChange.Data(it) }
                        )

                    }.onStart { GetPlaylistTracksChange.Loading }
            }
        val executeGetFollowing: suspend (String, String) -> Flow<PartialStateChange<HomeState>> =
            { userId, sessionToken ->
                homeInteractors.getFollowing(userId, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { GetFollowingChange.Error(it) },
                            ifRight = { GetFollowingChange.Data(it) }
                        )
                    }.onStart { GetFollowingChange.Loading }
            }
        val executeSearch: suspend (String) -> Flow<PartialStateChange<HomeState>> =
            { query ->
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
        val executeSelectPlaylist: suspend (playlist: Playlist) -> Flow<PartialStateChange<HomeState>> =
            { playlist ->
                flowOf(SelectPlaylistChange.Data(playlist))
            }
        return merge(
            filterIsInstance<HomeAction.ToggleLikeStreamTrack>()
                .flatMapConcat { executeToggleLikeStreamTrack(it.trackId, it.sessionToken) },
            filterIsInstance<HomeAction.ToggleLikeSearchTracks>()
                .flatMapConcat { executeToggleLike(it.trackId, it.sessionToken) },
            filterIsInstance<HomeAction.SelectTrack>()
                .flatMapConcat { flowOf(SelectTrackChange.Data(selectedTrack = it.selectedTrack)) },
            filterIsInstance<HomeAction.GetUser>()
                .flatMapConcat { executeGetUser(it.id, it.sessionToken) },
            filterIsInstance<HomeAction.SelectTab>()
                .flatMapConcat { flowOf(SelectTabChange.Data(it.tabIndex)) },
            filterIsInstance<HomeAction.QueryTextChanged>()
                .flatMapConcat { flowOf(TextChange.QueryTextChange(it.query)) },
            filterIsInstance<HomeAction.SelectPlaylist>()
                .flatMapConcat { executeSelectPlaylist(it.selectedPlaylist) },
            filterIsInstance<HomeAction.GetPlaylistTracks>()
                .flatMapConcat { executeGetPlaylistTracks(it.playlistUrl) },
            filterIsInstance<HomeAction.GetFollowing>()
                .flatMapConcat { executeGetFollowing(it.userId, it.sessionToken) },
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
                        it.track,
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
                    executeSearch(it.query)
                },
        )
    }

    fun onFollowClick(isSubscribing: Boolean, user: User, mainState: MainState) {
        if (!isSubscribing) {
            dispatch(
                HomeAction.FollowUser(
                    user,
                    mainState.sessionToken ?: throw IllegalArgumentException()
                )
            )
        } else {
            dispatch(
                HomeAction.UnfollowUser(
                    user,
                    mainState.sessionToken ?: throw IllegalArgumentException()
                )
            )
        }
    }
}