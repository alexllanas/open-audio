package com.alexllanas.openaudio.framework.network.responses

import com.alexllanas.core.data.remote.NetworkResponse
import com.google.gson.annotations.SerializedName

class PlaylistResponse(
    val id: String? = null,
    val name: String? = null,
    @SerializedName(value = "nbTracks")
    val trackCount: Int = 0,
    val author: UserResponse? = null
) : NetworkResponse
