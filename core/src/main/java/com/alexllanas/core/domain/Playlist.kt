package com.alexllanas.core.domain

import com.google.gson.annotations.SerializedName

class Playlist(
    val id: String? = null,
    val name: String? = null,
    @SerializedName(value = "nbTracks")
    val trackCount: Int = 0,
    val url: Int = 0,
    val author: User? = null
) {
    val coverImage: String?
        get() = id?.let { id -> "/img/playlist/$id" }
}
