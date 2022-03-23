package com.alexllanas.openaudio.framework.network.track

import com.alexllanas.core.data.remote.track.TrackRemoteServiceContract
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

    @GET("/api/post?action=insert&pl[id]={pId}&pl[name]={ptName}")
    override suspend fun addTrackToPlaylist(
        @Query("name") title: String,
        @Query("eId") mediaUrl: String,
        @Query("img") image: String,
        @Path("pName") playlistName: String,
        @Path("pId") playListId: String,
        @Header("Cookie") sessionToken: String
    ): TrackResponse

    @GET("/api/post?action=delete")
    override suspend fun deleteTrack(
        @Query("_id") trackId: String,
        @Header("Cookie") sessionToken: String
    )
}