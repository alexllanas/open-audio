package com.alexllanas.common.responses

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName(value = "_id")
    val id: String? = null,
    @SerializedName(value = "name")
    val title: String? = null,
    @SerializedName(value = "eId")
    val mediaUrl: String? = null,
    @SerializedName(value = "img")
    val image: String? = null,
    @SerializedName(value = "lov")
    val userLikeIds: List<String> = emptyList(),
    val liked: Boolean = false
)