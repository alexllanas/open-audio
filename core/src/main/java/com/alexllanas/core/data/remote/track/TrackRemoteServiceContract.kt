package com.alexllanas.core.data.remote.track

import javax.sound.midi.Track

interface TrackRemoteServiceContract {


    suspend fun addTrack(
        title: String,
        mediaUrl: String,
        imageUrl: String,
        sessionToken: String
    ): Any

    suspend fun addTrackToPlaylist(
        trackId: String,
        title: String,
        mediaUrl: String,
        image: String,
        playlistName: String,
        playListId: String,
        sessionToken: String
    ): Any

    suspend fun deleteTrack(
        trackId: String,
        sessionToken: String
    ): Any?

}