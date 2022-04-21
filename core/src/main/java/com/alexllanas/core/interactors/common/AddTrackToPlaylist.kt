package com.alexllanas.core.interactors.common

import arrow.core.Either
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

open class AddTrackToPlaylist(
    private val trackDataSource: TrackDataSource
) {
    suspend operator fun invoke(
        track: Track,
        playlistName: String,
        playListId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> = flow {
        trackDataSource.addTrackToPlaylist(
            id =  "",
            track.title ?: "",
            track.mediaUrl ?: "",
            track.image ?: "",
            playlistName,
            playListId,
            sessionToken
        )
    }
}