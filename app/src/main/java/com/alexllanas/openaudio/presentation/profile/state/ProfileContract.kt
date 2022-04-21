package com.alexllanas.openaudio.presentation.profile.state

import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.common.state.Action
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange

sealed class ProfileAction : Action() {
    data class ChangeAvatar(
        val imageFilePath: String,
        val sessionToken: String
    ) : ProfileAction()

    data class ChangeBio(
        val bio: String,
        val sessionToken: String
    ) : ProfileAction()

    data class ChangeLocation(
        val location: String,
        val sessionToken: String
    ) : ProfileAction()

    data class ChangeName(
        val name: String,
        val sessionToken: String
    ) : ProfileAction()

    data class ChangePassword(
        val currentPassword: String,
        val newPassword: String,
        val sessionToken: String
    ) : ProfileAction()

    data class Logout(
        val sessionToken: String
    ) : ProfileAction()

    data class NameTextChanged(val name: String) : ProfileAction()
    data class BioTextChanged(val bio: String) : ProfileAction()
    data class LocationTextChanged(val location: String) : ProfileAction()
    data class CurrentPasswordTextChanged(val currentPassword: String) : ProfileAction()
    data class NewPasswordTextChanged(val newPassword: String) : ProfileAction()
    data class ConfirmPasswordTextChanged(val confirmPassword: String) : ProfileAction()
}

sealed class ChangeInfo : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return when (this) {
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
            is Data -> state.copy(
                loggedInUser = user,
                isLoading = false,
                error = null
            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    object Loading : ChangeInfo()
    data class Data(val user: User) : ChangeInfo()
    data class Error(val throwable: Throwable) : ChangeInfo()
}

sealed class LogoutChange : PartialStateChange<MainState> {
    override fun reduce(state: MainState): MainState {
        return when (this) {
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
            Success -> state.copy(
                isLoading = false,
                error = null,
                loggedInUser = null,
                sessionToken = null
            )
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
        }
    }

    object Loading : LogoutChange()
    object Success : LogoutChange()
    data class Error(val throwable: Throwable) : LogoutChange()
}

sealed class TextChange : PartialStateChange<ProfileState> {
    override fun reduce(state: ProfileState): ProfileState {
        return when (this) {
            is NameTextChanged -> state.copy(
                nameText = name
            )
            is BioTextChanged -> state.copy(
                bioText = bio
            )
            is LocationTextChanged -> state.copy(
                locationText = location
            )
            is CurrentPasswordTextChanged -> state.copy(
                currentPasswordText = currentPassword
            )
            is NewPasswordTextChanged -> state.copy(
                newPasswordText = newPassword
            )
            is ConfirmPasswordTextChanged -> state.copy(
                confirmPasswordText = confirmPassword
            )
        }
    }

    data class NameTextChanged(val name: String) : TextChange()
    data class BioTextChanged(val bio: String) : TextChange()
    data class LocationTextChanged(val location: String) : TextChange()
    data class CurrentPasswordTextChanged(val currentPassword: String) : TextChange()
    data class NewPasswordTextChanged(val newPassword: String) : TextChange()
    data class ConfirmPasswordTextChanged(val confirmPassword: String) : TextChange()
}