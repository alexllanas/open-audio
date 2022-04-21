package com.alexllanas.openaudio.framework.mappers

import com.alexllanas.core.domain.models.Track
import com.alexllanas.openaudio.framework.network.models.TrackResponse

fun TrackResponse.toDomainTrack() = Track(
    id = id,
    title = title,
    mediaUrl = mediaUrl,
    image = image,
    userLikeIds = userLikeIds,
    liked = liked,
//    playlists = playlists.map { it.toDomainPlaylist() }
)