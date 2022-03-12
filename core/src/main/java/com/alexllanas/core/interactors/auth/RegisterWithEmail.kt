package com.alexllanas.core.interactors.auth

import com.alexllanas.core.data.UserDataSource
import com.alexllanas.core.domain.User

class RegisterWithEmail(private val userDataSource: UserDataSource) {

    suspend operator fun invoke(name: String, email: String, password: String): User =
        userDataSource.registerWithEmail(name, email, password)
}