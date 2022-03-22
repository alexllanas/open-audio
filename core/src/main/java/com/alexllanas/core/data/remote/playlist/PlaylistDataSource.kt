package com.alexllanas.core.data.remote.playlist

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track

interface PlaylistDataSource {

    suspend fun getPlaylistById(playlistId: String): Playlist

    suspend fun getPlaylistTracks(playlistUrl: String): List<Track>

    suspend fun createPlaylist(playlistName: String, sessionToken: String): Playlist

    suspend fun deletePlaylist(playlistId: String, sessionToken: String): Int

}