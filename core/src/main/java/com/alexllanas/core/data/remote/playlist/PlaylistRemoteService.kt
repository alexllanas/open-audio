package com.alexllanas.core.data.remote.playlist

interface PlaylistRemoteService<P, T> {
    suspend fun getPlaylistById(playlistId: String): P

    suspend fun getPlaylistTracks(userId: String, playlistNumber: String): List<T>

    suspend fun createPlaylist(playlistName: String, sessionToken: String): P

    suspend fun deletePlaylist(playlistId: String, sessionToken: String): Int
}