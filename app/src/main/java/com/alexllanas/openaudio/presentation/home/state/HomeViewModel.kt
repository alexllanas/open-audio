package com.alexllanas.openaudio.presentation.home.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.interactors.home.HomeInteractors
import com.alexllanas.core.util.Constants.Companion.TAG
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
                flow { emitAll(homeInteractors.getStream(sessionToken)) }
                    .map { result ->
                        result.fold(
                            ifLeft = { StreamChange.Error(it) },
                            ifRight = { StreamChange.Data(it) }
                        )
                    }.onStart { StreamChange.Loading }
            }
        val executeGetUserTracks: suspend (String) -> Flow<PartialStateChange<HomeState>> =
            { userId ->
                flow {
                    emitAll(homeInteractors.getUserTracks(userId))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { UserTracksChange.Error(it) },
                            ifRight = { UserTracksChange.Data(it) }
                        )
                    }.onStart { UserTracksChange.Loading }
            }
        val executeSearch: suspend (String) -> Flow<PartialStateChange<HomeState>> = { query ->
            flow {
                emitAll(homeInteractors.search(query))
            }
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

        return merge(
            filterIsInstance<HomeAction.LoadStream>()
                .flatMapLatest
                {
                    executeLoadStream(it.sessionToken)
                },
            filterIsInstance<HomeAction.GetUserTracks>()
                .flatMapLatest
                {
                    executeGetUserTracks(it.userId)
                },
            filterIsInstance<HomeAction.SearchAction>()
                .flatMapLatest
                {
                    executeSearch(it.query)
                },
        )
    }

    fun dispatch(action: HomeAction) {
        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }
}