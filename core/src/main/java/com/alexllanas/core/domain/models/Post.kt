package com.alexllanas.core.domain.models

data class Post(
    val loved : Boolean = false,
    val loversCount : Int = 0,
    val track: Track? = null,
)