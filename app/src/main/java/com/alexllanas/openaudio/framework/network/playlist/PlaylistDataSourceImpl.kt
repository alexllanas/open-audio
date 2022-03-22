package com.alexllanas.openaudio.framework.network.playlist

import com.alexllanas.core.data.remote.playlist.PlaylistDataSource
import com.alexllanas.core.data.remote.playlist.PlaylistRemoteService
import com.alexllanas.core.data.remote.user.UserRemoteService
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track

class PlaylistDataSourceImpl(
    private val playlistRemoteService: PlaylistRemoteService
) : PlaylistDataSource {
    override suspend fun getPlaylistById(playlistId: String): Playlist {
        TODO("Not yet implemented")
    }

    override suspend fun getPlaylistTracks(playlistUrl: String): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun createPlaylist(playlistName: String, sessionToken: String): Playlist {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlaylist(playlistId: String, sessionToken: String): Int {
        TODO("Not yet implemented")
    }
}