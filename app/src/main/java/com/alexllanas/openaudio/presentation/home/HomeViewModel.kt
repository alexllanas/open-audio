package com.alexllanas.openaudio.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.interactors.auth.Login
import com.alexllanas.core.interactors.home.GetStream
import com.alexllanas.core.interactors.home.GetUserTracks
import com.alexllanas.core.interactors.home.Search
import com.alexllanas.core.util.Constants.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

    private val initialState: HomeViewState by lazy { HomeViewState() }

    //    private val _state = MutableStateFlow(initialState)
//    val state = _state.asStateFlow()
    val state: StateFlow<HomeViewState>

    private val _actions = MutableSharedFlow<HomeAction>()
    private val actions = _actions.asSharedFlow()

    init {
//        bindActions()
        state =
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
    private fun SharedFlow<HomeAction>.toChangeFlow(): Flow<PartialStateChange> {
        val executeLoadStream: suspend (String) -> Flow<PartialStateChange> = { sessionToken ->
            flow { emitAll(getStream(sessionToken)) }
                .map { result ->
                    result.fold(
                        ifLeft = { PartialStateChange.StreamChange.Error(it) },
                        ifRight = { PartialStateChange.StreamChange.Data(it) }
                    )
                }.onStart { PartialStateChange.StreamChange.Loading }
        }
        val executeGetUserTracks: suspend (String) -> Flow<PartialStateChange> = { userId ->
            flow {
                emitAll(getUserTracks(userId))
            }
                .map { result ->
                    result.fold(
                        ifLeft = { PartialStateChange.UserTracksChange.Error(it) },
                        ifRight = { PartialStateChange.UserTracksChange.Data(it) }
                    )
                }.onStart { PartialStateChange.UserTracksChange.Loading }
        }
        return merge(
            filterIsInstance<HomeAction.LoadStreamAction>()
                .flatMapLatest
                {
                    executeLoadStream(it.sessionToken)
                },
            filterIsInstance<HomeAction.GetUserTracksAction>()
                .flatMapLatest
                {
                    executeGetUserTracks(it.userId)
                }
        )
    }

    fun dispatch(action: HomeAction) {
        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

//    private fun mergeFlows(): Flow<HomeChange> {
//        val getStream = actions.filterIsInstance<HomeAction.LoadStreamAction>()
//            .flatMapConcat {
//                flow {
//                    emitAll(getStream(it.sessionToken))
//                }
//                    .map { result ->
//                        result.fold(
//                            ifLeft = { ErrorChange(it) },
//                            ifRight = { PartialStateChange.StreamChange(it) }
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
//                            ifRight = { PartialStateChange.UserTracksChange(it) }
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
//                                PartialStateChange.SearchChange(
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
//                                PartialStateChange.LoginChange(loggedInUser = it)
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