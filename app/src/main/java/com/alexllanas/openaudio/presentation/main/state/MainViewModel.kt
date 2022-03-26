package com.alexllanas.openaudio.presentation.main.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.interactors.auth.AuthInteractors
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.LoginChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
) : ViewModel() {
    private val initialMainState: MainState by lazy { MainState() }
    val mainState: StateFlow<MainState>

    private val _actions = MutableSharedFlow<AuthAction>()
    private val actions = _actions.asSharedFlow()

    init {
        mainState =
            actions
                .toChangeFlow()
                .scan(initialMainState) { state, change -> change.reduce(state) }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Eagerly,
                    initialMainState
                )

    }

    fun dispatch(action: AuthAction) {
        Log.d(Constants.TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun SharedFlow<AuthAction>.toChangeFlow(): Flow<PartialStateChange<MainState>> {

        val executeLogin: suspend (String, String) -> Flow<PartialStateChange<MainState>> =
            { email, password ->
                flow {
                    emitAll(authInteractors.login(email, password))
                }
                    .map { result ->
                        result.fold(
                            ifLeft = { LoginChange.Error(it) },
                            ifRight = { LoginChange.Data(it) }
                        )
                    }.onStart { LoginChange.Loading }
            }

        return merge(
            filterIsInstance<AuthAction.Login.LoginAction>()
                .flatMapLatest {
                    executeLogin(it.email, it.password)
                }
        )
    }


}