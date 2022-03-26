package com.alexllanas.core.interactors.profile

import arrow.core.Either
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow

class ChangeLocation(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(
        location: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> = userDataSource.changeLocation(location, sessionToken)
}