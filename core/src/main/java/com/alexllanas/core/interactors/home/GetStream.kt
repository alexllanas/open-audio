package com.alexllanas.core.interactors.home

import arrow.core.Either
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

class GetStream(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(sessionToken: String) = userDataSource.getStream(sessionToken)
}