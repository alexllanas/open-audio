package com.alexllanas.core.interactors.home

import com.alexllanas.core.data.remote.user.UserDataSource

class GetUserTracks(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(userId: String) = userDataSource.getTracks(userId)
}
