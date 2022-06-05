package com.alexllanas.openaudio.framework.network.track

import com.alexllanas.core.data.remote.track.TrackRemoteServiceContract
import com.alexllanas.core.domain.models.TrackMetadata
import com.alexllanas.openaudio.framework.network.models.TrackResponse
import retrofit2.http.*

interface TrackApiService : TrackRemoteServiceContract {

    @GET("/api/post?action=insert")
    override suspend fun addTrack(
        @Query("name") title: String,
        @Query("eId") mediaUrl: String,
        @Query("img") imageUrl: String,
        @Header("Cookie") sessionToken: String
    ): TrackResponse

    @GET("/api/post?action=insert")
    override suspend fun addTrackToPlaylist(
        @Query("_id") trackId: String,
        @Query("name") title: String,
        @Query("eId") mediaUrl: String,
        @Query("img") image: String,
        @Query("pl[name]") playlistName: String,
        @Query("pl[id]") playListId: String,
        @Header("Cookie") sessionToken: String
    ): TrackResponse

    @GET("https://youtube.com/oembed?")
    override suspend fun getTrackMetadata(
        @Query("url") url: String
    ): TrackMetadata

    @GET("/api/post?action=delete")
    override suspend fun deleteTrack(
        @Query("_id") trackId: String,
        @Header("Cookie") sessionToken: String
    ): TrackResponse?
}