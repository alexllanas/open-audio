package com.alexllanas.core.data

import com.alexllanas.core.domain.Playlist
import com.alexllanas.core.domain.Track

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