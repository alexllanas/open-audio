package com.alexllanas.openaudio.framework.network.track

import arrow.core.Either
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

class TrackDataSourceImpl(
    private val trackApiService: TrackApiService
) : TrackDataSource {
    override suspend fun addTrack(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTrackToPlaylist(
        title: String,
        mediaUrl: String,
        image: String,
        playlist: Playlist,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrack(trackId: String, sessionToken: String) {
        TODO("Not yet implemented")
    }
}