package com.alexllanas.core.data.remote.track

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track

interface TrackDataSource {

    suspend fun addTrack(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ): Track

    suspend fun addTrackToPlaylist(
        title: String,
        mediaUrl: String,
        image: String,
        playlist: Playlist,
        sessionToken: String
    ): Track

    suspend fun deleteTrack(
        trackId: String,
        sessionToken: String
    )
}