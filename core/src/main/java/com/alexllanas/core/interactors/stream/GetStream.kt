package com.alexllanas.core.interactors.stream

import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.data.remote.user.UserRemoteService
import com.alexllanas.core.domain.Track
import kotlinx.coroutines.flow.Flow

class GetStream(private val userDataSource: UserDataSource) {
    suspend operator fun invoke(sessionToken: String): Flow<List<Track>> = userDataSource.getStream(sessionToken)
}