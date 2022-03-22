package com.alexllanas.openaudio.presentation.changes

import com.alexllanas.core.domain.Track

sealed class HomeChange {
    object Loading : HomeChange()
    data class Stream(val stream: List<Track>) : HomeChange()
    data class Error(val throwable: Throwable) : HomeChange()

}