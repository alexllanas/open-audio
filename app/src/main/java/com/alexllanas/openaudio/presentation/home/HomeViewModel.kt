package com.alexllanas.openaudio.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.interactors.home.GetStream
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.actions.HomeAction
import com.alexllanas.openaudio.presentation.changes.HomeChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStream: GetStream
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
        is HomeChange.Stream -> state.copy(
            stream = change.stream,
            isLoading = false,
        )
        is HomeChange.Error -> state.copy(
            isLoading = false,
            isError = false
        )
        HomeChange.Loading -> state.copy(
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

    @OptIn(FlowPreview::class)
    fun bindActions() {
        viewModelScope.launch {
            actions.filterIsInstance<HomeAction.LoadStream>()
                .flatMapConcat {
                    flow {
                        emitAll(getStream(it.sessionToken))
                    }
                        .map { result ->
                            result.fold(
                                ifLeft = { HomeChange.Error(it) },
                                ifRight = { HomeChange.Stream(it) }
                            )
                        }
                }.onStart { HomeChange.Loading }
                .scan(initialState) { state, change -> reduce(state, change) }
                .onEach { Log.d(TAG, "bindActions: ${it.stream.size}") }
//                .catch { Log.d(TAG, "bindActions: ERROR: $it") }
                .collect {
                    _state.value = it
                    state
                }
        }
    }
}