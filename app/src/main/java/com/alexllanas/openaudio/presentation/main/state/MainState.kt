package com.alexllanas.openaudio.presentation.main.state

import android.os.Parcelable
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import kotlinx.android.parcel.Parcelize

data class MainState(
    val loggedInUser: User? = null,
    val sessionToken: String? = null,
    val currentPlayingTrack: Track? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)