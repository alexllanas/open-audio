package com.alexllanas.openaudio.presentation.upload.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.interactors.upload.UploadInteractors
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.presentation.home.state.*
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.floor

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadInteractors: UploadInteractors
) : ViewModel() {

    private val initialState: UploadState by lazy { UploadState() }

    val uploadState: StateFlow<UploadState>

    private val _actions = MutableSharedFlow<UploadAction>()
    private val actions = _actions.asSharedFlow()

    init {
        uploadState =
            actions
                .toChangeFlow()
                .scan(initialState) { state, change -> change.reduce(state) }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Eagerly,
                    initialState
                )
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private fun SharedFlow<UploadAction>.toChangeFlow(): Flow<PartialStateChange<UploadState>> {
        val executeUploadTrack: suspend (String, String, String, String) -> Flow<PartialStateChange<UploadState>> =
            { title, mediaUrl, image, sessionToken ->
                uploadInteractors.addTrack(title, mediaUrl, image, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { UploadTrackChange.Error(it) },
                            ifRight = { UploadTrackChange.Data(it) }
                        )
                    }.onStart { UploadTrackChange.Loading }
            }
        return merge(
            filterIsInstance<UploadAction.SetUrlText>()
                .flatMapConcat { flowOf(SetUrlTextChange.Data(it.url)) },
            filterIsInstance<UploadAction.UploadTrack>()
                .flatMapConcat {
                    executeUploadTrack(
                        it.title,
                        it.trackUrl,
                        it.image,
                        it.sessionToken
                    )
                }
        )
    }

    fun dispatch(action: UploadAction) {
        Log.d(Constants.TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }
}