package com.alexllanas.core.data.remote.track

interface TrackRemoteServiceContract {


    suspend fun addTrack(
        title: String,
        mediaUrl: String,
        imageUrl: String,
        sessionToken: String
    ): Any

    suspend fun addTrackToPlaylist(
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
    )

}