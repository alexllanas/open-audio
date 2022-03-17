package com.alexllanas.common.responses

import com.google.gson.annotations.SerializedName

class PlaylistResponse(
    val id: String? = null,
    val name: String? = null,
    @SerializedName(value = "nbTracks")
    val trackCount: Int = 0,
    val author: UserResponse? = null
)
