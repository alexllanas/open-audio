package com.alexllanas.core.interactors.common

import com.alexllanas.core.data.remote.track.TrackDataSource

class UnlikeTrack(private val trackDataSource: TrackDataSource) {
    suspend operator fun invoke(trackId: String, sessionToken: String) =
        trackDataSource.deleteTrack(trackId, sessionToken)
}