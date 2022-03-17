package com.alexllanas.common.responses

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("q")
    val query: String = "",

    @SerializedName("results")
    val results: SearchResponseBody? = null
)

data class SearchResponseBody(
    @SerializedName("playlist")
    val playlists: List<PlaylistResponse> = emptyList(),
    @SerializedName("user")
    val users: List<UserResponse> = emptyList(),
    @SerializedName("track")
    val tracks: List<TrackResponse> = emptyList() ,
    @SerializedName("post")
    val posts: List<TrackResponse>  = emptyList(),
)