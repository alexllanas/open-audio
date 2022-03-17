package com.alexllanas.core.data.remote.common

import kotlinx.coroutines.flow.Flow

interface CommonDataSource {

    /**
     * Returns 4 lists: Tracks, Playlists, Users, Posts (Tracks)
     */
    suspend fun search(query: String): Flow<HashMap<String, List<*>>>

}