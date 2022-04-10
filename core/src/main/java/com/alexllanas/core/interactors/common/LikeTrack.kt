package com.alexllanas.core.interactors.common

import arrow.core.Either
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class LikeTrack(private val trackDataSource: TrackDataSource) {

    /**
     * Add track to 'Liked Songs' playlist, create if does not exist.
     */
    suspend operator fun invoke(
        track: Track,
        sessionToken: String,
        loggedInUser: User
    ): Flow<Either<Throwable, Track>> = flow {
        val likedPlaylist = loggedInUser.playlists.find { it.name == "Liked Songs" }
        emitAll(
            trackDataSource.addTrackToPlaylist(
                id = track.id ?: "",
                track.title ?: "",
                track.mediaUrl ?: "",
                track.image ?: "",
                "Liked Songs",
                likedPlaylist?.id ?: "create",
                sessionToken
            )
        )
    }
}