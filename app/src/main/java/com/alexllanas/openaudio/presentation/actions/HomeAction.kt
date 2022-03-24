package com.alexllanas.openaudio.presentation.actions

import com.alexllanas.openaudio.presentation.state.BaseAction

sealed class HomeAction : BaseAction {
    data class LoginAction(val email: String, val password: String) : HomeAction()
    data class LoadStreamAction(val sessionToken: String) : HomeAction()
    data class GetUserTracksAction(val userId: String) : HomeAction()
    data class SearchAction(val query: String) : HomeAction()
}