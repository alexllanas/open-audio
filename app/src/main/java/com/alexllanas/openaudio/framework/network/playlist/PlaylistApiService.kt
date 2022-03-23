package com.alexllanas.openaudio.framework.network.playlist

import com.alexllanas.core.data.remote.playlist.PlaylistRemoteServiceContract
import com.alexllanas.openaudio.framework.network.models.PlaylistResponse
import com.alexllanas.openaudio.framework.network.models.TrackResponse
import retrofit2.http.*

interface PlaylistApiService : PlaylistRemoteServiceContract {

    @GET("/api/playlist/{id}")
    override suspend fun getPlaylistById(@Path("id") playlistId: String): PlaylistResponse

    @GET("/u/{uId}/playlist/{pId}?format=json")
    override suspend fun getPlaylistTracks(
        @Path("uId") userId: String,
        @Path("pId") playlistNumber: String
    ): List<TrackResponse>

    @FormUrlEncoded
    @GET("/api/playlist")
    override suspend fun createPlaylist(
        @Field("name") playlistName: String,
        @Field("action") action: String,
        @Header("Cookie") sessionToken: String
    ): PlaylistResponse

    @FormUrlEncoded
    @GET("/api/playlist")
    override suspend fun deletePlaylist(
        @Field("id") id: String,
        @Field("action") action: String,
        @Header("Cookie") sessionToken: String
    ): Int
}