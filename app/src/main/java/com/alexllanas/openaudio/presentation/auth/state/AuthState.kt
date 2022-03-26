package com.alexllanas.openaudio.presentation.auth.state

data class AuthState(
    val loginEmailText: String = "",
    val loginPasswordText: String = "",
    val registerNameText: String = "",
    val registerEmailText: String = "",
    val registerPasswordText: String = ""
)