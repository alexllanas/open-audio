package com.alexllanas.openaudio.presentation.profile.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.interactors.profile.ProfileInteractors
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.TextChange
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange
import com.alexllanas.openaudio.presentation.profile.state.TextChange.*
import com.alexllanas.openaudio.presentation.upload.state.UploadAction
import com.alexllanas.openaudio.presentation.upload.state.UploadState
import com.alexllanas.openaudio.presentation.upload.state.UploadTrackChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileInteractors: ProfileInteractors
) : ViewModel() {
    private val initialState: ProfileState by lazy { ProfileState() }

    val profileState: StateFlow<ProfileState>

    private val _actions = MutableSharedFlow<ProfileAction>()
    private val actions = _actions.asSharedFlow()

    init {
        profileState =
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
    private fun SharedFlow<ProfileAction>.toChangeFlow(): Flow<PartialStateChange<ProfileState>> {
        return merge(
            filterIsInstance<ProfileAction.NameTextChanged>()
                .map {
                    NameTextChanged(
                        it.name
                    )
                },
            filterIsInstance<ProfileAction.BioTextChanged>()
                .map {
                    BioTextChanged(
                        it.bio
                    )
                },
            filterIsInstance<ProfileAction.LocationTextChanged>()
                .map {
                    LocationTextChanged(
                        it.location
                    )
                },
            filterIsInstance<ProfileAction.CurrentPasswordTextChanged>()
                .map {
                    CurrentPasswordTextChanged(
                        it.currentPassword
                    )
                },
            filterIsInstance<ProfileAction.NewPasswordTextChanged>()
                .map {
                    NewPasswordTextChanged(
                        it.newPassword
                    )
                },
            filterIsInstance<ProfileAction.ConfirmPasswordTextChanged>()
                .map {
                    ConfirmPasswordTextChanged(
                        it.confirmPassword
                    )
                }
        )
    }

    fun dispatch(action: ProfileAction) {
        Log.d(Constants.TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }
}