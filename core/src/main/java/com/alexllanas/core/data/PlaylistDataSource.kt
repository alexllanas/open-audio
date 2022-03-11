package com.alexllanas.core.data

import com.alexllanas.core.domain.Playlist
import com.alexllanas.core.domain.Track

interface PlaylistDataSource {

    suspend fun getPlaylistById(playlistId: String): Playlist

    suspend fun getPlaylistTracks(playlistUrl: String): List<Track>

    suspend fun createPlaylist(playlistName: String, sessionToken: String): Playlist

    suspend fun deletePlaylist(playlistId: String, sessionToken: String): Int

}