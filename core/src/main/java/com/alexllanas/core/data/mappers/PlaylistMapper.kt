package com.alexllanas.core.data.mappers

import com.alexllanas.common.responses.PlaylistResponse
import com.alexllanas.common.responses.TrackResponse
import com.alexllanas.core.domain.Playlist
import com.alexllanas.core.domain.Track

fun PlaylistResponse.toDomainPlaylist(): Playlist =
    Playlist(
        id = id,
        name = name,
        trackCount = trackCount,
    ).also { playlist ->
        playlist.author = author?.toDomainUser()
    }