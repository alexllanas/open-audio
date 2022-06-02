package com.alexllanas.core.interactors.upload

import com.alexllanas.core.data.remote.track.TrackDataSource

class GetTrackMetadata(private val trackDataSource: TrackDataSource) {
    suspend operator fun invoke(
        mediaUrl: String
    ) = trackDataSource.getTrackMetadata(mediaUrl)
}