package com.alexllanas.core.domain.models

import com.alexllanas.core.util.Constants

data class Playlist(
    val id: String? = null,
    val name: String? = null,
    val trackCount: Int = 0,
    val url: String? = null
) {
    val coverImage: String?
        get() = id?.let { id -> "${Constants.BASE_URL}/img/playlist/$id" }
//    val url: String
//        get() {
//            val y = id
//            val x =  "/u/" + id?.replace("_", "/playlist/")
//            return x
//        }
    var author: User? = null
}
