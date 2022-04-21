package com.alexllanas.openaudio.framework.mappers

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Post
import com.alexllanas.openaudio.framework.network.models.PlaylistResponse
import com.alexllanas.openaudio.framework.network.models.PostResponse

fun PostResponse.toDomainPost(): Post =
    Post(
        loved = loved,
        loversCount = loversCount,
        track = track?.toDomainTrack()
    )