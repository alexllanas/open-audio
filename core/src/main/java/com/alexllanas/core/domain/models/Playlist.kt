package com.alexllanas.core.domain.models

class Playlist(
    val id: String? = null,
    val name: String? = null,
    val trackCount: Int = 0,
) {
    val coverImage: String?
        get() = id?.let { id -> "/img/playlist/$id" }
    val url: String
        get() = "/u/" + id?.replace("_", "/playlist/")
    var author: User? = null
}
