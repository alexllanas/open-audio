package com.alexllanas.openaudio.presentation.auth.state

import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.common.state.Action
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange

sealed class AuthAction : Action() {

    data class SetSessionTokenAction(val token: String) : AuthAction()
    object ClearMainState : AuthAction()

    sealed class Login {
        data class LoginAction(val email: String, val password: String) : AuthAction()
        data class EmailTextChangedAction(val email: String) : AuthAction()
        data class PasswordTextChangedAction(val password: String) : AuthAction()
    }

    sealed class Register {
        data class RegisterWithEmailAction(
            val name: String,
            val email: String,
            val password: String
        )

        data class NameTextChangedAction(val name: String) : AuthAction()
        data class EmailTextChangedAction(val email: String) : AuthAction()
        data class PasswordTextChangedAction(val password: String) : AuthAction()
    }
}

class ClearMainStateChange : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return MainState()
    }
}

sealed class SetSessionTokenChange : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return when (this) {
            is Data -> state.copy(
                sessionToken = token
            )
        }
    }

    data class Data(val token: String) : SetSessionTokenChange()
}

sealed class RegisterWithEmailChange : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return when (this) {
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                isLoading = false,
                error = null,
                loggedInUser = loggedInUser
            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    object Loading : RegisterWithEmailChange()
    data class Data(val loggedInUser: User) : RegisterWithEmailChange()
    data class Error(val throwable: Throwable) : RegisterWithEmailChange()
}

sealed class TextChange : PartialStateChange<AuthState> {
    override fun reduce(state: AuthState): AuthState {
        return when (this) {
            is LoginEmailChange -> state.copy(
                loginEmailText = email
            )
            is LoginPasswordChange -> state.copy(
                loginPasswordText = password
            )
            is RegisterNameChange -> state.copy(
                registerNameText = name
            )
            is RegisterEmailChange -> state.copy(
                registerEmailText = email
            )
            is RegisterPasswordChange -> state.copy(
                registerPasswordText = password
            )
        }
    }

    data class LoginEmailChange(val email: String) : TextChange()
    data class LoginPasswordChange(val password: String) : TextChange()
    data class RegisterNameChange(val name: String) : TextChange()
    data class RegisterEmailChange(val email: String) : TextChange()
    data class RegisterPasswordChange(val password: String) : TextChange()
}

sealed class LoginChange : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return when (this) {
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                isLoading = false,
                error = null,
                loggedInUser = loggedInUser,
            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    object Loading : LoginChange()
    data class Data(val loggedInUser: User) : LoginChange()
    data class Error(val throwable: Throwable) : LoginChange()
}
