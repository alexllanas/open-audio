package com.alexllanas.openaudio.framework.network.track

import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track

class TrackDataSourceImpl : TrackDataSource {
    override suspend fun addTrack(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ): Track {
        TODO("Not yet implemented")
    }

    override suspend fun addTrackToPlaylist(
        title: String,
        mediaUrl: String,
        image: String,
        playlist: Playlist,
        sessionToken: String
    ): Track {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrack(trackId: String, sessionToken: String) {
        TODO("Not yet implemented")
    }
}