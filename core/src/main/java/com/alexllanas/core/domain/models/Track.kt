package com.alexllanas.core.domain.models

data class Track(
    val id: String? = null,
    val title: String? = null,
    val mediaUrl: String? = null,
    val image: String? = null,
    val userLikeIds: List<String> = emptyList(),
    var liked: Boolean = false
)

fun Track.setIsLiked(block: () -> Boolean) {
    liked = block()
}

fun Track.canLike(): Boolean {
    id?.let {
        title?.let {
            mediaUrl?.let {
                return true
            }
        }
    }
    return false
}