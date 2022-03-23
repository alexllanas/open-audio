package com.alexllanas.core.interactors.auth

import arrow.core.Either
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow

class RegisterWithEmail(private val userDataSource: UserDataSource) {

    suspend operator fun invoke(name: String, email: String, password: String): Flow<Either<Throwable, User>> =
        userDataSource.registerWithEmail(name, email, password)
}