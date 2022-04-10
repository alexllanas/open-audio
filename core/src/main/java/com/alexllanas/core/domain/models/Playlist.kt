package com.alexllanas.core.domain.models

import com.alexllanas.core.util.Constants

class Playlist(
    val id: String? = null,
    val name: String? = null,
    val trackCount: Int = 0,
) {
    val coverImage: String?
        get() = id?.let { id -> "${Constants.BASE_URL}/img/playlist/$id" }
    val url: String
        get() = "u/" + id?.replace("_", "/playlist")
    var author: User? = null
}
