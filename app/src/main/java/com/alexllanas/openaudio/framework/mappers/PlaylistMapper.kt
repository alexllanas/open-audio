package com.alexllanas.openaudio.framework.mappers

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.openaudio.framework.network.models.PlaylistResponse

fun PlaylistResponse.toDomainPlaylist(): Playlist =
    Playlist(
        id = id,
        name = name,
        trackCount = trackCount,
    ).also { playlist ->
        playlist.author = author?.toDomainUser()
    }