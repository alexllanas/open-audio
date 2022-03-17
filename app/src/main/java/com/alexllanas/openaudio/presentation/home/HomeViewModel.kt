package com.alexllanas.openaudio.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.common.Intent
import com.alexllanas.core.domain.Track
import com.alexllanas.core.interactors.stream.GetStream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStream: GetStream
) : ViewModel() {
    val viewState: StateFlow<HomeViewState>
    private val intentMutableFlow = MutableSharedFlow<Intent>()
    protected val intentFlow: SharedFlow<Intent> get() = intentMutableFlow


    init {
        val initialViewState = HomeViewState.initial()

        viewState =
            intentFlow.filterIsInstance<StreamIntent.Initial>().take(1)
                .toViewState()
                .scan(initialViewState) { state, change -> change.reduce(state) }
                .stateIn(viewModelScope, SharingStarted.Eagerly, initialViewState)
    }

    private fun Flow<Intent>.toViewState(): Flow<PartialStateChange> {
        val execute: suspend (String) -> Flow<PartialStateChange> = { sessionToken ->
            getStream(sessionToken)
                .map { result ->
                    PartialStateChange.Success(result)
                }
        }
        val sessionToken = filterIsInstance<StreamIntent.Initial>()
            .map { it.sessionToken }
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        return sessionToken.flatMapLatest(execute)
    }

    suspend fun processIntent(intent: Intent) = intentMutableFlow.emit(intent)
}

internal sealed interface PartialStateChange {
    data class Success(val stream: List<Track>) : PartialStateChange

    fun reduce(state: HomeViewState): HomeViewState = when (this) {
        is Success -> state.copy(
            stream = stream
        )
    }
}