package com.alexllanas.openaudio.presentation.home.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.interactors.auth.Login
import com.alexllanas.core.interactors.home.GetStream
import com.alexllanas.core.interactors.home.GetUserTracks
import com.alexllanas.core.interactors.home.HomeInteractors
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
//    private val login: Login,
    private val homeInteractors: HomeInteractors
) : ViewModel() {

    private val initialState: HomeState by lazy { HomeState() }

    val state: StateFlow<HomeState>

    private val _actions = MutableSharedFlow<HomeAction>()
    private val actions = _actions.asSharedFlow()

    init {
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
            flow { emitAll(homeInteractors.getStream(sessionToken)) }
                .map { result ->
                    result.fold(
                        ifLeft = { PartialStateChange.StreamChange.Error(it) },
                        ifRight = { PartialStateChange.StreamChange.Data(it) }
                    )
                }.onStart { PartialStateChange.StreamChange.Loading }
        }
        val executeGetUserTracks: suspend (String) -> Flow<PartialStateChange> = { userId ->
            flow {
                emitAll(homeInteractors.getUserTracks(userId))
            }
                .map { result ->
                    result.fold(
                        ifLeft = { PartialStateChange.UserTracksChange.Error(it) },
                        ifRight = { PartialStateChange.UserTracksChange.Data(it) }
                    )
                }.onStart { PartialStateChange.UserTracksChange.Loading }
        }
        val executeSearch: suspend (String) -> Flow<PartialStateChange> = { query ->
            flow {
                emitAll(homeInteractors.search(query))
            }
                .map { result ->
                    result.fold(
                        ifLeft = { PartialStateChange.SearchChange.Error(it) },
                        ifRight = {
                            PartialStateChange.SearchChange.Data(
                                searchTrackResults = it["tracks"] as List<Track>,
                                searchPlaylistResults = it["playlists"] as List<Playlist>,
                                searchUserResults = it["users"] as List<User>,
                                searchPostResults = it["posts"] as List<Track>
                            )
                        }
                    )
                }.onStart { PartialStateChange.SearchChange.Loading }
        }
//        val executeLogin: suspend (String, String) -> Flow<PartialStateChange> =
//            { email, password ->
//                flow {
//                    emitAll(login(email, password))
//                }
//                    .map { result ->
//                        result.fold(
//                            ifLeft = { PartialStateChange.LoginChange.Error(it) },
//                            ifRight = { PartialStateChange.LoginChange.Data(it) }
//                        )
//                    }.onStart { PartialStateChange.LoginChange.Loading }
//            }

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
                },
            filterIsInstance<HomeAction.SearchAction>()
                .flatMapLatest
                {
                    executeSearch(it.query)
                },
//            filterIsInstance<HomeAction.LoginAction>()
//                .flatMapLatest {
//                    executeLogin(it.email, it.password)
//                }
        )
    }

    fun dispatch(action: HomeAction) {
        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }
}