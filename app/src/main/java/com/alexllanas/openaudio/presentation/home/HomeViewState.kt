package com.alexllanas.openaudio.presentation.home

import com.alexllanas.core.domain.Track

data class HomeViewState(
    val stream: List<Track>
) {
    companion object {
        fun initial() = HomeViewState(
            stream = emptyList()
        )
    }
}
