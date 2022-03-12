package com.alexllanas.core.interactors.auth

import com.alexllanas.core.data.UserDataSource
import com.alexllanas.core.domain.User

class Login(private val userDataSource: UserDataSource) {

    suspend operator fun invoke(email: String, password: String): User =
        userDataSource.login(email, password)

}