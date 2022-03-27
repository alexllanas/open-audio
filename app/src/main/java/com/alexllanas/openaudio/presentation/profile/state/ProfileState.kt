package com.alexllanas.openaudio.presentation.profile.state

import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User

data class ProfileState(
    val avatarUrl: String= "",
    val nameText: String= "",
    val locationText: String= "",
    val bioText: String= "",
    val currentPasswordText: String= "",
    val newPasswordText: String= "",
    val confirmPasswordText: String= "",
    val isLoading: Boolean = false,
    val error: Throwable? = null
)