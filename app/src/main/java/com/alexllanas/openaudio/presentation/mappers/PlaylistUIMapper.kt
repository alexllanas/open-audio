package com.alexllanas.openaudio.presentation.mappers

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.openaudio.presentation.models.PlaylistUIModel

fun Playlist.toUI(): PlaylistUIModel =
    PlaylistUIModel(name, coverImage, author?.toUI())