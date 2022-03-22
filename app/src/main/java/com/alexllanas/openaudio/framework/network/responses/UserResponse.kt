package com.alexllanas.openaudio.framework.network.responses

import com.alexllanas.core.data.remote.NetworkResponse
import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName(value = "id", alternate = ["_id", "tId", "uId"])
    val id: String? = null,
    @SerializedName(value = "name", alternate = ["n", "tNm", "uNm"])
    val name: String? = null,
    val email: String? = null,
    @SerializedName("pl")
    val playlists: List<PlaylistResponse> = emptyList(),
    val tracks: List<TrackResponse> = emptyList(),
    val subscriptions: List<UserResponse> = emptyList(),
    val subscribers: List<UserResponse> = emptyList(),
    @SerializedName("nbSubscriptions")
    val subscriptionCount: Int = 0,
    @SerializedName("nbSubscribers")
    val subscriberCount: Int = 0,
    val lastTrack: TrackResponse? = null,
    @SerializedName("loc")
    val location: String? = null,
    val bio: String? = null,

    // true if the logged-in user has subscribed to this user
    val isSubscribing: Boolean = false
): NetworkResponse
