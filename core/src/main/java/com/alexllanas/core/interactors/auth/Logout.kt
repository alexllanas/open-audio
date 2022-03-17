package com.alexllanas.core.interactors.auth

import com.alexllanas.core.data.remote.user.UserDataSource

class Logout(private val userDataSource: UserDataSource) {

    suspend operator fun invoke(sessionToken: String) = userDataSource.logout(sessionToken)
}