package com.alexllanas.openaudio.presentation.upload.state

import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.TrackMetadata

data class UploadState(
    val uploadedTrackResults: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)