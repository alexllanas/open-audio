package com.alexllanas.core.data.remote.common

import arrow.core.Either
import com.alexllanas.core.domain.models.GeneralError.*
import kotlinx.coroutines.flow.Flow

interface CommonDataSource {

    /**
     * Returns 4 lists: Tracks, Playlists, Users, Posts (Tracks)
     */
    suspend fun search(query: String): Flow<Either<NetworkError, HashMap<String, List<*>>>>

}