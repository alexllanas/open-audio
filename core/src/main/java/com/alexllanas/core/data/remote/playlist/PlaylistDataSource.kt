package com.alexllanas.core.data.remote.playlist

import arrow.core.Either
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistDataSource {

    suspend fun getPlaylistById(playlistId: String): Flow<Either<Throwable, Playlist>>

    suspend fun getPlaylistTracks(playlistUrl: String): Flow<Either<Throwable, List<Track>>>

    suspend fun createPlaylist(
        playlistName: String,
        sessionToken: String
    ): Flow<Either<Throwable, Playlist>>

    suspend fun deletePlaylist(
        playlistId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Int>>

}