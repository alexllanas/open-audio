package com.alexllanas.openaudio.presentation.main.state

import com.alexllanas.openaudio.presentation.home.state.HomeState


interface PartialStateChange<BaseState> {
    fun reduce(state: BaseState): BaseState
}

interface BaseState