package com.alexllanas.openaudio.presentation.auth.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.interactors.auth.AuthInteractors
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val initialAuthState: AuthState by lazy { AuthState() }

    val authState: StateFlow<AuthState>

    private val _actions = MutableSharedFlow<AuthAction>()
    private val actions = _actions.asSharedFlow()

    init {
        authState = actions
            .toChangeFlow()
            .scan(initialAuthState) { state, change -> change.reduce(state) }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                initialAuthState
            )
    }

    fun dispatch(action: AuthAction) {
        Log.d(Constants.TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun SharedFlow<AuthAction>.toChangeFlow(): Flow<PartialStateChange<AuthState>> {
        return merge(
            filterIsInstance<AuthAction.Login.EmailTextChangedAction>()
                .map {
                    TextChange.LoginEmailChange(email = it.email)
                },
            filterIsInstance<AuthAction.Login.PasswordTextChangedAction>()
                .map {
                    TextChange.LoginPasswordChange(it.password)
                },
            filterIsInstance<AuthAction.Register.NameTextChangedAction>()
                .map {
                    TextChange.RegisterNameChange(it.name)
                },
            filterIsInstance<AuthAction.Register.EmailTextChangedAction>()
                .map {
                    TextChange.RegisterEmailChange(it.email)
                },
            filterIsInstance<AuthAction.Register.PasswordTextChangedAction>()
                .map {
                    TextChange.RegisterPasswordChange(it.password)
                }
        )
    }

}