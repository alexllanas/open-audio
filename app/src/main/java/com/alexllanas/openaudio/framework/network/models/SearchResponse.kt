package com.alexllanas.openaudio.framework.network.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("q")
    val query: String = "",

    @SerializedName("results")
    val results: SearchResponseBody? = null
) {
    data class SearchResponseBody(
        @SerializedName("playlists")
        val playlists: List<PlaylistResponse> = emptyList(),
        @SerializedName("users")
        val users: List<UserResponse> = emptyList(),
        @SerializedName("tracks")
        val tracks: List<TrackResponse> = emptyList(),
        @SerializedName("posts")
        val posts: List<TrackResponse> = emptyList(),
    )
}

