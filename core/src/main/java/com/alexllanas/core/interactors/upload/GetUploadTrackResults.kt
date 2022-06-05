package com.alexllanas.core.interactors.upload

import com.alexllanas.core.data.remote.home.HomeDataSource
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.getResult
import kotlinx.coroutines.flow.map
import java.lang.IllegalArgumentException

class GetUploadTrackResults(
    private val trackDataSource: TrackDataSource,
    private val homeDataSource: HomeDataSource
) {
    suspend operator fun invoke(
        mediaUrl: String
    ) = trackDataSource.getTrackMetadata(mediaUrl).map { metadata ->
        val uploadTrackResults = arrayListOf<Track>()
        val newTrack = Track(
            id = Constants.NEW_TRACK_ID,
            title = metadata.title,
            image = metadata.thumbnailUrl,
            mediaUrl = "/yt/" + Regex(Constants.VIDEO_ID_REGEX).find(mediaUrl)?.groupValues?.get(0)
                ?: throw IllegalArgumentException(
                    "No video ID found in URL: $mediaUrl"
                )
        )
        uploadTrackResults.add(newTrack)

        newTrack.title?.let { title ->
            homeDataSource.search(title).map { result ->
                result.fold(
                    ifLeft = {
                        throw Exception(Constants.SEARCHING_ERROR)
                    },
                    ifRight = { resultMap ->
                        uploadTrackResults.addAll(
                            resultMap["tracks"]?.filterIsInstance<Track>() ?: emptyList()
                        )
                    }
                )
            }
        }
        uploadTrackResults
    }.getResult()
}