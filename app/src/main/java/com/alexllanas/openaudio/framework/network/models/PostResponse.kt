package com.alexllanas.openaudio.framework.network.models

import com.alexllanas.core.domain.models.Track
import com.google.gson.annotations.SerializedName

data class PostResponse(
    val loved: Boolean = false,
    @SerializedName(value = "lovers")
    val loversCount: Int = 0,
    @SerializedName(value = "post")
    val track: TrackResponse? = null,
)