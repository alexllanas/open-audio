package com.alexllanas.openaudio.presentation.mappers

import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.presentation.models.UserUIModel

fun User.toUI(): UserUIModel =
    UserUIModel(name, avatarUrl)