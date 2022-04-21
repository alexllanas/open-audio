package com.alexllanas.openaudio.presentation.upload.state

import android.hardware.biometrics.BiometricManager
import com.alexllanas.core.domain.models.Track
import com.alexllanas.openaudio.presentation.common.state.Action
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange

sealed class UploadAction : Action() {
    data class UploadTrack(
        val trackUrl: String,
        val title: String,
        val image: String,
        val sessionToken: String
    ) : UploadAction()

    data class SetUrlText(val url: String) : UploadAction()
}

sealed class SetUrlTextChange : PartialStateChange<UploadState> {
    override fun reduce(state: UploadState): UploadState {
        return when (this) {
            is Data -> {
                state.copy(trackUrl = url)
            }
        }
    }

    data class Data(val url: String) : SetUrlTextChange()
}

sealed class UploadTrackChange : PartialStateChange<UploadState> {
    override fun reduce(state: UploadState): UploadState {
        return when (this) {
            is Data -> {
                // TODO("Refresh logged in user to sync new track")
                state.copy(
                    isLoading = false,
                    error = null
                )
            }
            is Error -> state.copy(
                isLoading = false,
                error = throwable
            )
            Loading -> state.copy(
                isLoading = true,
                error = null
            )
        }
    }

    data class Error(val throwable: Throwable) : UploadTrackChange()
    data class Data(val track: Track) : UploadTrackChange()
    object Loading : UploadTrackChange()
}
