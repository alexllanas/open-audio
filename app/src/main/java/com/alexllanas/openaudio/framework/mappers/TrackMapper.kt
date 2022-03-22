package com.alexllanas.openaudio.framework.mappers

import com.alexllanas.core.domain.Track
import com.alexllanas.openaudio.framework.network.responses.TrackResponse

fun TrackResponse.toDomainTrack() = Track(
    id = id,
    title = title,
    mediaUrl = mediaUrl,
    image = image,
    userLikeIds = userLikeIds,
    liked = liked
)