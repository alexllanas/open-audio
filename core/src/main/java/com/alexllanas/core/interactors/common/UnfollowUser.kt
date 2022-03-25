package com.alexllanas.core.interactors.common

import com.alexllanas.core.data.remote.user.UserDataSource

class UnfollowUser(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(
        userId: String,
        sessionToken: String
    ) = userDataSource.unfollowUser(userId, sessionToken)
}