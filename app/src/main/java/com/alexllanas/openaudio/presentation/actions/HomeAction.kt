package com.alexllanas.openaudio.presentation.actions

import com.alexllanas.openaudio.presentation.state.BaseAction

sealed class HomeAction : BaseAction {
    data class LoadStream(val sessionToken: String) : HomeAction()
    data class GetUserTracks(val userId: String) : HomeAction()
}