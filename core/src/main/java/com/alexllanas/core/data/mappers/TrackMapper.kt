package com.alexllanas.core.data.mappers

import com.alexllanas.common.responses.TrackResponse
import com.alexllanas.core.domain.Track

fun TrackResponse.toDomainTrack() = Track(
    id = id,
    title = title,
    mediaUrl = mediaUrl,
    image = image,
    userLikeIds = userLikeIds,
    liked = liked
)