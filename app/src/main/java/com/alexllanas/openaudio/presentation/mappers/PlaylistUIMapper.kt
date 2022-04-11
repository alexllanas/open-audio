package com.alexllanas.openaudio.presentation.mappers

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.openaudio.presentation.models.PlaylistUIModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Playlist.toUI(): PlaylistUIModel =
    PlaylistUIModel(
        name,
        URLEncoder.encode(coverImage, StandardCharsets.UTF_8.toString()),
        URLEncoder.encode(url, StandardCharsets.UTF_8.toString()),
        author?.toUI()
    )