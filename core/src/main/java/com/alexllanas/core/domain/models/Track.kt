package com.alexllanas.core.domain.models

data class Track(
    val id: String? = null,
    val title: String? = null,
    val mediaUrl: String? = null,
    val image: String? = null,
    val userLikeIds: List<String> = emptyList(),
    val liked: Boolean = false
)