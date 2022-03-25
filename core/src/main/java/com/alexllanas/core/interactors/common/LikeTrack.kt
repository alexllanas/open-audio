package com.alexllanas.core.interactors.common

import arrow.core.Either
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LikeTrack(private val trackDataSource: TrackDataSource) {

    /**
     * Add track to 'Liked Songs' playlist, create if does not exist.
     */
    suspend operator fun invoke(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String,
        loggedInUser: User
    ): Flow<Either<Throwable, Track>> = flow {
        val likedPlaylist = loggedInUser.playlists.find { it.name == "Liked Songs" }
        trackDataSource.addTrackToPlaylist(
            title,
            mediaUrl,
            image,
            "Liked Songs",
            likedPlaylist?.id ?: "create",
            sessionToken
        )
    }
}