package com.alexllanas.core.data.remote.playlist

interface PlaylistRemoteServiceContract {
    suspend fun getPlaylistById(playlistId: String): Any

    suspend fun getPlaylistTracks(userId: String, playlistNumber: String): List<Any>

    suspend fun createPlaylist(
        playlistName: String,
        action: String = "create",
        sessionToken: String
    ): Any

    suspend fun deletePlaylist(
        id: String,
        action: String = "delete",
        sessionToken: String
    ): Any
}