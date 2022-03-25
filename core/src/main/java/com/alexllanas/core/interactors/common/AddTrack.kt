package com.alexllanas.core.interactors.common

import com.alexllanas.core.data.remote.track.TrackDataSource

class AddTrack(private val trackDataSource: TrackDataSource) {
    suspend operator fun invoke(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ) = trackDataSource.addTrack(title, mediaUrl, image, sessionToken)
}