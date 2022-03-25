package com.alexllanas.core.data.remote.home

import arrow.core.Either
import com.alexllanas.core.domain.models.GeneralError.*
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    /**
     * Returns 4 lists: Tracks, Playlists, Users, Posts (Tracks)
     */
    suspend fun search(query: String): Flow<Either<Throwable, HashMap<String, List<*>>>>

}