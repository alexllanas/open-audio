package com.alexllanas.core.data

interface CommonDataSource {

    /**
     * Returns map of 4 lists: Tracks, Playlists, Users, Posts (Tracks)
     */
    suspend fun search(query: String) : HashMap<String, List<*>>

}