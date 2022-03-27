package com.alexllanas.openaudio.presentation.upload.state

import com.alexllanas.core.domain.models.Track

data class UploadState(
    val trackUrl: String = "",
    val trackTitle: String = "",
    val uploadedTrack: Track? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)