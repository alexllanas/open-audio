package com.alexllanas.core.interactors.common

import arrow.core.Either
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

open class AddTrackToPlaylist(private val trackDataSource: TrackDataSource) {
    suspend operator fun invoke(
        title: String,
        mediaUrl: String,
        image: String,
        playlistName: String,
        playListId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> =
        trackDataSource.addTrackToPlaylist(
            title,
            mediaUrl,
            image,
            playlistName,
            playListId,
            sessionToken
        )
}