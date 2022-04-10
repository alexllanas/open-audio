package com.alexllanas.openaudio.presentation.main.state

import com.alexllanas.core.domain.models.User

data class MainState(
    val loggedInUser: User? = null,
    val sessionToken: String? = "whydSid=s%3Alcz9zAORxGMGh--F54iY6W-B-6Dh2GaX.dcbKBd8CjbvZNKiUzqrI3WaQrXW4qy3Xtm%2FQVZQWFjI",
    val isLoading: Boolean = false,
    val error: Throwable? = null
)