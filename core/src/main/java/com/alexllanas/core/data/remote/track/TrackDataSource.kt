package com.alexllanas.core.data.remote.track

import arrow.core.Either
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.TrackMetadata
import kotlinx.coroutines.flow.Flow

interface TrackDataSource {

    suspend fun addTrack(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>>

    suspend fun addTrackToPlaylist(
        id: String,
        title: String,
        mediaUrl: String,
        image: String,
        playlistName: String,
        playListId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>>

    suspend fun getTrackMetadata(
        mediaUrl: String
    ): Flow<TrackMetadata>

    suspend fun deleteTrack(
        trackId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>>
}