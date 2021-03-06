package com.alexllanas.openaudio.framework.network.models

import com.google.gson.annotations.SerializedName

class PlaylistResponse(
    val id: String? = null,
    val name: String? = null,
    val url: String? = null,
    @SerializedName(value = "nbTracks")
    val trackCount: Int = 0,
    val author: UserResponse? = null
)
