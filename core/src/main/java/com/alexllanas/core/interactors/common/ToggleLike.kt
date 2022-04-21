package com.alexllanas.core.interactors.common

import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.data.remote.user.UserDataSource

class ToggleLike(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(
        trackId: String,
        sessionToken: String
    ) = userDataSource.toggleLike(trackId, sessionToken)
}