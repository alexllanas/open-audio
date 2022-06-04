package com.alexllanas.core.interactors.upload

import com.alexllanas.core.data.remote.home.HomeDataSource
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.getResult
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetUploadTrackResults(
    private val trackDataSource: TrackDataSource,
    private val homeDataSource: HomeDataSource
) {
    suspend operator fun invoke(
        mediaUrl: String
    ) = flow {
        trackDataSource.getTrackMetadata(mediaUrl).map { metadata ->
            val uploadTrackResults = arrayListOf<Track>()
            val newTrack = Track(
                id = Constants.NEW_TRACK_ID,
                title = metadata.title,
                image = metadata.thumbnailUrl,
                mediaUrl = metadata.mediaUrl
            )
            uploadTrackResults.add(newTrack)

            newTrack.title?.let { title ->
                homeDataSource.search(title).map { result ->
                    result.fold(
                        ifLeft = {
                            throw Exception(Constants.SEARCHING_ERROR)
                        },
                        ifRight = { resultMap ->
                            uploadTrackResults.addAll(resultMap["tracks"] as List<Track>)
                        }
                    )
                }
            }
            emit(uploadTrackResults)
        }
    }.getResult()
}