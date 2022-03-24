package com.alexllanas.openaudio.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.interactors.auth.Login
import com.alexllanas.core.interactors.home.GetStream
import com.alexllanas.core.interactors.home.GetUserTracks
import com.alexllanas.core.interactors.home.Search
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.actions.HomeAction
import com.alexllanas.openaudio.presentation.changes.HomeChange
import com.alexllanas.openaudio.presentation.changes.HomeChange.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val login: Login,
    private val getStream: GetStream,
    private val getUserTracks: GetUserTracks,
    private val search: Search
) : ViewModel() {

    private val initialState = HomeViewState()

    private val _state = MutableStateFlow(initialState)
    val viewState: StateFlow<HomeViewState>

    private val _actions = MutableSharedFlow<HomeAction>()
    private val actions = _actions

    init {
        val getStream = actions.filterIsInstance<HomeAction.LoadStreamAction>()
            .flatMapConcat {
                flow {
                    emitAll(getStream(it.sessionToken))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { ErrorChange(it) },
                            ifRight = { StreamChange(it) }
                        )
                    }
            }
        val getUserTracks = actions.filterIsInstance<HomeAction.GetUserTracksAction>()
            .flatMapConcat {
                flow {
                    emitAll(getUserTracks(it.userId))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { ErrorChange(it) },
                            ifRight = { UserTracksChange(it) }
                        )
                    }
            }
        val search = actions.filterIsInstance<HomeAction.SearchAction>()
            .flatMapConcat {
                flow {
                    emitAll(search(it.query))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { ErrorChange(it) },
                            ifRight = {
                                SearchChange(
                                    searchTrackResults = it["tracks"] as List<Track>,
                                    searchPlaylistResults = it["playlists"] as List<Playlist>,
                                    searchUserResults = it["users"] as List<User>,
                                    searchPostResults = it["posts"] as List<Track>
                                )
                            }
                        )
                    }
            }
        val login = actions.filterIsInstance<HomeAction.LoginAction>()
            .flatMapConcat {
                flow {
                    emitAll(login(it.email, it.password))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { ErrorChange(it) },
                            ifRight = {
                                LoginChange(loggedInUser = it)
                            }
                        )
                    }
            }

        viewState = merge(
            getStream,
            login,
            getUserTracks,
            search
        )
            .scan(initialState) { state, change -> reduce(state, change) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

//        bindActions()
    }

    private fun reduce(state: HomeViewState, change: HomeChange): HomeViewState = when (change) {
        is LoginChange -> state.copy(
            loggedInUser = change.loggedInUser,
            isLoading = false
        )
        is StreamChange -> state.copy(
            stream = change.stream,
            isLoading = false,
        )
        is UserTracksChange -> state.copy(
            userTracks = change.userTracks,
            isLoading = false
        )
        is SearchChange -> state.copy(
            searchTrackResults = change.searchTrackResults,
            searchPlaylistResults = change.searchPlaylistResults,
            searchUserResults = change.searchUserResults,
            searchPostResults = change.searchPostResults,
            isLoading = false
        )
        is ErrorChange -> state.copy(
            isLoading = false,
            isError = false
        )
        is Loading -> state.copy(
            isLoading = true,
            stream = emptyList(),
            isError = false
        )
    }


    fun dispatch(action: HomeAction) {
        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

//    private fun bindActions() {
//        viewModelScope.launch {
//            mergeFlows().onStart { Loading }
//                .scan(initialState) { state, change -> reduce(state, change) }
//                .collect {
//                    _state.value = it
//                    state
//                }
//        }
//    }

//    private fun mergeFlows(): StateFlow<HomeViewState> {
//        val getStream = actions.filterIsInstance<HomeAction.LoadStreamAction>()
//            .flatMapConcat {
//                flow {
//                    emitAll(getStream(it.sessionToken))
//                }
//                    .map { result ->
//                        result.fold(
//                            ifLeft = { ErrorChange(it) },
//                            ifRight = { StreamChange(it) }
//                        )
//                    }
//            }
//        val getUserTracks = actions.filterIsInstance<HomeAction.GetUserTracksAction>()
//            .flatMapConcat {
//                flow {
//                    emitAll(getUserTracks(it.userId))
//                }
//                    .map { result ->
//                        result.fold(
//                            ifLeft = { ErrorChange(it) },
//                            ifRight = { UserTracksChange(it) }
//                        )
//                    }
//            }
//        val search = actions.filterIsInstance<HomeAction.SearchAction>()
//            .flatMapConcat {
//                flow {
//                    emitAll(search(it.query))
//                }
//                    .map { result ->
//                        result.fold(
//                            ifLeft = { ErrorChange(it) },
//                            ifRight = {
//                                SearchChange(
//                                    searchTrackResults = it["tracks"] as List<Track>,
//                                    searchPlaylistResults = it["playlists"] as List<Playlist>,
//                                    searchUserResults = it["users"] as List<User>,
//                                    searchPostResults = it["posts"] as List<Track>
//                                )
//                            }
//                        )
//                    }
//            }
//        val login = actions.filterIsInstance<HomeAction.LoginAction>()
//            .flatMapConcat {
//                flow {
//                    emitAll(login(it.email, it.password))
//                }
//                    .map { result ->
//                        result.fold(
//                            ifLeft = { ErrorChange(it) },
//                            ifRight = {
//                                LoginChange(loggedInUser = it)
//                            }
//                        )
//                    }
//            }
//
//        return merge(
//            login,
//            getStream,
//            getUserTracks,
//            search
//        )
//    }
}