package com.alexllanas.core.data.remote.playlist

interface PlaylistRemoteService {
    suspend fun getPlaylistById(playlistId: String): Any

    suspend fun getPlaylistTracks(userId: String, playlistNumber: String): List<Any>

    suspend fun createPlaylist(playlistName: String, sessionToken: String): Any

    suspend fun deletePlaylist(playlistId: String, sessionToken: String): Int
}