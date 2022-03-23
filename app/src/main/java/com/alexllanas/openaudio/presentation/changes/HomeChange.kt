package com.alexllanas.openaudio.presentation.changes

import com.alexllanas.core.domain.models.Track

sealed class HomeChange {
    object Loading : HomeChange()
    data class Stream(val stream: List<Track>) : HomeChange()
    data class Error(val throwable: Throwable) : HomeChange()
    data class UserTracks(val userTracks: List<Track>) : HomeChange()

}