package com.alexllanas.openaudio.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.interactors.home.GetStream
import com.alexllanas.core.interactors.home.GetUserTracks
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.actions.HomeAction
import com.alexllanas.openaudio.presentation.changes.HomeChange
import com.alexllanas.openaudio.presentation.changes.HomeChange.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStream: GetStream,
    private val getUserTracks: GetUserTracks
) : ViewModel() {

    private val initialState = HomeViewState()

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<HomeAction>()
    private val actions = _actions

    init {
        bindActions()
    }

    private fun reduce(state: HomeViewState, change: HomeChange): HomeViewState = when (change) {
        is Stream -> state.copy(
            stream = change.stream,
            isLoading = false,
        )
        is UserTracks -> state.copy(
            userTracks = change.userTracks,
            isLoading = false
        )
        is Error -> state.copy(
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

    private fun bindActions() {
        viewModelScope.launch {
            mergeFlows().onStart { Loading }
                .scan(initialState) { state, change -> reduce(state, change) }
                .collect {
                    _state.value = it
                    state
                }
        }
    }

    private fun mergeFlows(): Flow<HomeChange> {
        val getStream = actions.filterIsInstance<HomeAction.LoadStream>()
            .flatMapConcat {
                flow {
                    emitAll(getStream(it.sessionToken))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { Error(it) },
                            ifRight = { Stream(it) }
                        )
                    }
            }
        val getUserTracks = actions.filterIsInstance<HomeAction.GetUserTracks>()
            .flatMapConcat {
                flow {
                    emitAll(getUserTracks(it.userId))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { Error(it) },
                            ifRight = { UserTracks(it) }
                        )
                    }
            }
        return merge(
            getStream,
            getUserTracks
        )
    }
}