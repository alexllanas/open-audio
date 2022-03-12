package com.alexllanas.core.data

import com.alexllanas.common.utils.NetworkResponse

interface CommonDataSource {

    /**
     * Returns 4 lists: Tracks, Playlists, Users, Posts (Tracks)
     */
    suspend fun search(query: String) : NetworkResponse

}