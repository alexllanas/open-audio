package com.alexllanas.openaudio.framework.network.playlist

import com.alexllanas.core.data.remote.playlist.PlaylistRemoteServiceContract
import com.alexllanas.core.util.Constants.Companion.PAGE_LIMIT
import com.alexllanas.openaudio.framework.network.models.PlaylistResponse
import com.alexllanas.openaudio.framework.network.models.TrackResponse
import retrofit2.http.*

interface PlaylistApiService : PlaylistRemoteServiceContract {

    @GET("/api/playlist/{id}")
    override suspend fun getPlaylist(@Path("id") playlistId: String): PlaylistResponse

    @GET(" {playlistUrl}?limit=${PAGE_LIMIT}&format=json")
    override suspend fun getPlaylistTracks(
        @Path("playlistUrl", encoded = true) playlistUrl: String
    ): List<TrackResponse>

    @FormUrlEncoded
    @POST("/api/playlist")
    override suspend fun createPlaylist(
        @Field("name") playlistName: String,
        @Field("action") action: String,
        @Header("Cookie") sessionToken: String
    ): PlaylistResponse

    @FormUrlEncoded
    @POST("/api/playlist")
    override suspend fun deletePlaylist(
        @Field("id") id: String,
        @Field("action") action: String,
        @Header("Cookie") sessionToken: String
    ): Int
}