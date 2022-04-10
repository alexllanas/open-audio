package com.alexllanas.core.domain.models

import com.alexllanas.core.util.Constants

data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val playlists: List<Playlist> = emptyList(),
    val tracks: List<Track> = emptyList(),
    val subscriptions: List<User> = emptyList(),
    val subscribers: List<User> = emptyList(),
    val subscriptionCount: Int = 0,
    val subscriberCount: Int = 0,
    val lastTrack: Track? = null,
    val location: String? = null,
    val bio: String? = null,

    // true if the logged-in user has subscribed to this user
    val isSubscribing: Boolean = false
) {
    val avatarUrl: String?
        get() = id?.let { id -> "${Constants.BASE_URL}/img/u/$id" }
}
