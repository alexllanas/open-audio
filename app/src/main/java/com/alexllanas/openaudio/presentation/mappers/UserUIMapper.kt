package com.alexllanas.openaudio.presentation.mappers

import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.models.UserUIModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun User.toUI(): UserUIModel =
    UserUIModel(
        name, URLEncoder.encode(avatarUrl, StandardCharsets.UTF_8.toString()),
    )