package com.alexllanas.core.interactors.stream

import arrow.core.Either
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

class GetStream(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(sessionToken: String): Flow<Either<Throwable, List<Track>>> = userDataSource.getStream(sessionToken)
}