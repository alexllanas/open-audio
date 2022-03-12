package com.alexllanas.core.domain

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName(value = "id", alternate = ["_id", "tId", "uId"])
    val id: String? = null,
    @SerializedName(value = "name", alternate = ["n", "tNm", "uNm"])
    val name: String? = null,
    val email: String? = null,
    @SerializedName("pl")
    val playlists: List<Playlist> = emptyList(),
    val tracks: List<Track> = emptyList(),
    val subscriptions: List<User> = emptyList(),
    val subscribers: List<User> = emptyList(),
    @SerializedName("nbSubscriptions")
    val subscriptionCount: Int = 0,
    @SerializedName("nbSubscribers")
    val subscriberCount: Int = 0,
    val lastTrack: Track? = null,
    @SerializedName("loc")
    val location: String? = null,
    val bio: String? = null,

    // true if the logged-in user has subscribed to this user
    val isSubscribing: Boolean = false

) {
        val avatarUrl: String?
            get() = id?.let { id -> "/img/u/$id" }
}
