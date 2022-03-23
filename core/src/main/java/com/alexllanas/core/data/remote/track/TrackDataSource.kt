package com.alexllanas.core.data.remote.track

import arrow.core.Either
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackDataSource {

    suspend fun addTrack(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>>

    suspend fun addTrackToPlaylist(
        title: String,
        mediaUrl: String,
        image: String,
        playlist: Playlist,
        sessionToken: String
    ): Flow<Either<Throwable, Track>>

    suspend fun deleteTrack(
        trackId: String,
        sessionToken: String
    )
}