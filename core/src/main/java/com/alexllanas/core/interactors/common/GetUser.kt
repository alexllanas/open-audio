package com.alexllanas.core.interactors.common

import com.alexllanas.core.data.remote.user.UserDataSource

class GetUser(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(
        userId: String,
        sessionToken: String
    ) = userDataSource.getUser(userId, sessionToken)
}