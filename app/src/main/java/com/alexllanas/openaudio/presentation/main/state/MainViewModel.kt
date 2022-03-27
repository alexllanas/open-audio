package com.alexllanas.openaudio.presentation.main.state

import android.util.Log
import androidx.compose.ui.semantics.SemanticsProperties.Error
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.computations.result
import com.alexllanas.core.interactors.auth.AuthInteractors
import com.alexllanas.core.interactors.home.HomeInteractors
import com.alexllanas.core.interactors.profile.ChangeAvatar
import com.alexllanas.core.interactors.profile.ChangeBio
import com.alexllanas.core.interactors.profile.ProfileInteractors
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.getResult
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.LoginChange
import com.alexllanas.openaudio.presentation.home.state.AddTrackToPlaylistChange
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.profile.state.ChangeInfo
import com.alexllanas.openaudio.presentation.profile.state.LogoutChange
import com.alexllanas.openaudio.presentation.profile.state.ProfileAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authInteractors: AuthInteractors,
    private val homeInteractors: HomeInteractors,
    private val profileInteractors: ProfileInteractors
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

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private fun SharedFlow<AuthAction>.toChangeFlow(): Flow<PartialStateChange<MainState>> {
        val executeLogin: suspend (String, String) -> Flow<PartialStateChange<MainState>> =
            { email, password ->
                authInteractors.login(email, password)
                    .map { result ->
                        result.fold(
                            ifLeft = { LoginChange.Error(it) },
                            ifRight = { LoginChange.Data(it) }
                        )
                    }.onStart { LoginChange.Loading }
            }
        val executeAddTrackToPlaylist: suspend (String, String, String, String, String, String) -> Flow<PartialStateChange<MainState>> =
            { title, mediaUrl, image, playlistName, playlistId, sessionToken ->
                homeInteractors.addTrackToPlaylist(
                    title,
                    mediaUrl,
                    image,
                    playlistName,
                    playlistId,
                    sessionToken
                )
                    .map { result ->
                        result.fold(
                            ifLeft = { AddTrackToPlaylistChange.Error(it) },
                            ifRight = { AddTrackToPlaylistChange.Data(it) }
                        )
                    }.onStart { AddTrackToPlaylistChange.Loading }
            }
        val executeLogout: suspend (String) -> Flow<PartialStateChange<MainState>> =
            { sessionToken ->
                flow {
                    profileInteractors.logout(sessionToken)
                    emit(LogoutChange.Success)
                }.onStart { LogoutChange.Loading }
            }
        val executeChangeBio: suspend (String, String) -> Flow<PartialStateChange<MainState>> =
            { bio, sessionToken ->
                profileInteractors.changeBio(bio, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ChangeInfo.Error(it) },
                            ifRight = { ChangeInfo.Data(it) }
                        )
                    }.onStart { ChangeInfo.Loading }
            }
        val executeChangeName: suspend (String, String) -> Flow<PartialStateChange<MainState>> =
            { name, sessionToken ->
                profileInteractors.changeName(name, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ChangeInfo.Error(it) },
                            ifRight = { ChangeInfo.Data(it) }
                        )
                    }.onStart { ChangeInfo.Loading }
            }
        val executeChangeLocation: suspend (String, String) -> Flow<PartialStateChange<MainState>> =
            { location, sessionToken ->
                profileInteractors.changeLocation(location, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ChangeInfo.Error(it) },
                            ifRight = { ChangeInfo.Data(it) }
                        )
                    }.onStart { ChangeInfo.Loading }
            }
        val executeChangeAvatar: suspend (String, String) -> Flow<PartialStateChange<MainState>> =
            { imageFilePath, sessionToken ->
                profileInteractors.changeAvatar(imageFilePath, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ChangeInfo.Error(it) },
                            ifRight = { ChangeInfo.Data(it) }
                        )
                    }.onStart { ChangeInfo.Loading }
            }
        val executeChangePassword: suspend (String, String, String) -> Flow<PartialStateChange<MainState>> =
            { currentPassword, newPassword, sessionToken ->
                profileInteractors
                    .changePassword(currentPassword, newPassword, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ChangeInfo.Error(it) },
                            ifRight = { ChangeInfo.Data(it) }
                        )
                    }.onStart { ChangeInfo.Loading }
            }
        return merge(
            filterIsInstance<ProfileAction.ChangeAvatar>()
                .flatMapConcat { executeChangeAvatar(it.imageFilePath, it.sessionToken) },
            filterIsInstance<ProfileAction.ChangePassword>()
                .flatMapConcat {
                    executeChangePassword(
                        it.currentPassword,
                        it.newPassword,
                        it.sessionToken
                    )
                },
            filterIsInstance<ProfileAction.ChangeName>()
                .flatMapConcat { executeChangeName(it.name, it.sessionToken) },
            filterIsInstance<ProfileAction.ChangeBio>()
                .flatMapConcat { executeChangeBio(it.bio, it.sessionToken) },
            filterIsInstance<ProfileAction.ChangeLocation>()
                .flatMapConcat { executeChangeLocation(it.location, it.sessionToken) },
            filterIsInstance<ProfileAction.Logout>()
                .flatMapConcat { executeLogout(it.sessionToken) },
            filterIsInstance<HomeAction.AddTrackToPlaylist>()
                .flatMapConcat {
                    executeAddTrackToPlaylist(
                        it.title,
                        it.mediaUrl,
                        it.image,
                        it.playlistName,
                        it.playListId,
                        it.sessionToken
                    )
                },
            filterIsInstance<AuthAction.Login.LoginAction>()
                .flatMapLatest {
                    executeLogin(it.email, it.password)
                }
        )
    }
}