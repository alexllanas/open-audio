package com.alexllanas.openaudio.framework.network.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName(value = "redirect")
    val redirect: String?,
    @SerializedName(value = "user")
    val user: UserResponse?
)
