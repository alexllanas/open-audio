package com.alexllanas.openaudio.framework.responses

import com.alexllanas.common.utils.NetworkResponse
import com.alexllanas.core.domain.Playlist
import com.alexllanas.core.domain.Track
import com.alexllanas.core.domain.User
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("q")
    val query: String = "",

    @SerializedName("results")
    val results: SearchResponseBody? = null
) : NetworkResponse

data class SearchResponseBody(
    @SerializedName("playlist")
    val playlists: List<Playlist> = emptyList(),
    @SerializedName("user")
    val users: List<User> = emptyList(),
    @SerializedName("track")
    val tracks: List<Track> = emptyList() ,
    @SerializedName("post")
    val posts: List<Track?>  = emptyList(),
)