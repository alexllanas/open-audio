package com.alexllanas.openaudio.presentation.home

import com.alexllanas.core.domain.models.Track
import com.alexllanas.openaudio.presentation.state.BaseState

data class HomeViewState(
    val stream: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : BaseState
