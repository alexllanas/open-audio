package com.alexllanas.core.interactors.auth

import com.alexllanas.core.data.UserDataSource
import com.alexllanas.core.domain.User

class Logout(private val userDataSource: UserDataSource) {

    suspend operator fun invoke(sessionToken: String) = userDataSource.logout(sessionToken)
}