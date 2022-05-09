package com.alexllanas.openaudio.presentation.mappers

import com.alexllanas.core.domain.models.Track
import com.alexllanas.openaudio.presentation.models.TrackUIList
import com.alexllanas.openaudio.presentation.models.TrackUIModel

fun Track.toUI(): TrackUIModel =
    TrackUIModel(id, title, image, mediaUrl, userLikeIds, liked)

fun TrackUIModel.toDomain(): Track =
    Track(id, title, mediaUrl, image, userLikeIds, liked)

fun List<Track?>.toUI(): TrackUIList {
    val tracks = arrayListOf<TrackUIModel>()
    forEach { track ->
        track?.toUI()?.let { tracks.add(it) }
    }
    return TrackUIList(tracks)
}

fun TrackUIList.toDomain(): List<Track> {
    val copy = arrayListOf<Track>()
    this.tracks.forEach { trackUIModel ->
        trackUIModel?.toDomain()?.let { track ->
            copy.add(track)
        }
    }
    return copy
}

