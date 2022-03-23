package com.alexllanas.openaudio.framework.network.playlist

import com.alexllanas.core.data.remote.playlist.PlaylistRemoteService
import com.alexllanas.openaudio.framework.network.models.PlaylistResponse
import com.alexllanas.openaudio.framework.network.models.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistApiService : PlaylistRemoteService<PlaylistResponse, TrackResponse> {

    @GET("/api/playlist/{id}")
    override suspend fun getPlaylistById(@Path("id") playlistId: String): PlaylistResponse


    @GET("/u/{uId}/playlist/{pId}?format=json")
    override suspend fun getPlaylistTracks(
        @Path("uId") userId: String,
        @Path("pId") playlistNumber: String
    ): List<TrackResponse>

    override suspend fun createPlaylist(
        playlistName: String,
        sessionToken: String
    ): PlaylistResponse

    override suspend fun deletePlaylist(playlistId: String, sessionToken: String): Int
}