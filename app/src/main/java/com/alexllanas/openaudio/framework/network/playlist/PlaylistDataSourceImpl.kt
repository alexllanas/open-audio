package com.alexllanas.openaudio.framework.network.playlist

import arrow.core.Either
import com.alexllanas.core.data.remote.playlist.PlaylistDataSource
import com.alexllanas.core.data.remote.playlist.PlaylistRemoteServiceContract
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.getResult
import com.alexllanas.openaudio.framework.mappers.toDomainPlaylist
import com.alexllanas.openaudio.framework.mappers.toDomainTrack
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@OptIn(FlowPreview::class)
class PlaylistDataSourceImpl(
    private val playlistApiService: PlaylistApiService
) : PlaylistDataSource {
    override suspend fun getPlaylist(playlistId: String): Flow<Either<Throwable, Playlist>> =
        suspend {
            playlistApiService
                .getPlaylist(playlistId)
                .toDomainPlaylist()
        }.asFlow().getResult()

    override suspend fun getPlaylistTracks(playlistUrl: String): Flow<Either<Throwable, List<Track>>> =
        suspend {
            playlistApiService
                .getPlaylistTracks(playlistUrl)
                .map { it.toDomainTrack() }
        }.asFlow().getResult()

    override suspend fun createPlaylist(
        playlistName: String,
        sessionToken: String
    ): Flow<Either<Throwable, Playlist>> =
        suspend {
            playlistApiService
                .createPlaylist(
                    playlistName = playlistName,
                    action = "create",
                    sessionToken = sessionToken
                )
                .toDomainPlaylist()
        }.asFlow().getResult()

    override suspend fun deletePlaylist(
        playlistId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>> =
        suspend {
            val responseId = playlistApiService
                .deletePlaylist(
                    id = playlistId,
                    sessionToken = sessionToken
                )
            responseId == playlistId.toInt()
        }.asFlow().getResult()
}