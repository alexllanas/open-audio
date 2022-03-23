package com.alexllanas.openaudio.framework.network.playlist

import arrow.core.Either
import com.alexllanas.core.data.remote.playlist.PlaylistDataSource
import com.alexllanas.core.data.remote.playlist.PlaylistRemoteServiceContract
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistDataSourceImpl(
    private val playlistApiService: PlaylistApiService
) : PlaylistDataSource {
    override suspend fun getPlaylistById(playlistId: String): Flow<Either<Throwable, Playlist>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlaylistTracks(playlistUrl: String): Flow<Either<Throwable, List<Track>>> {
        TODO("Not yet implemented")
    }

    override suspend fun createPlaylist(
        playlistName: String,
        sessionToken: String
    ): Flow<Either<Throwable, Playlist>> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlaylist(
        playlistId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Int>> {
        TODO("Not yet implemented")
    }
}