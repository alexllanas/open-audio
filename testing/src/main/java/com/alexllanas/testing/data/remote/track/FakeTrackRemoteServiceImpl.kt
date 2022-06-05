package com.alexllanas.testing.data.remote.track

import com.alexllanas.core.data.remote.track.TrackRemoteServiceContract
import com.alexllanas.core.domain.models.TrackMetadata
import com.alexllanas.core.util.Constants
import java.lang.Exception
import java.util.*

class FakeTrackRemoteServiceImpl : TrackRemoteServiceContract {
    override suspend fun addTrack(
        title: String,
        mediaUrl: String,
        imageUrl: String,
        sessionToken: String
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun addTrackToPlaylist(
        trackId: String,
        title: String,
        mediaUrl: String,
        image: String,
        playlistName: String,
        playListId: String,
        sessionToken: String
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun getTrackMetadata(videoId: String): TrackMetadata {
        if (videoId == Constants.SAMPLE_MEDIA_URL) {
            return TrackMetadata(
                id = Constants.NEW_TRACK_ID,
                title = Constants.SAMPLE_TRACK_TITLE,
                mediaUrl = videoId,
                thumbnailUrl = Constants.SAMPLE_TRACK_THUMBNAIL_URL
            )
        } else {
            throw Exception(Constants.GET_TRACK_METADATA_ERROR)
        }
    }

    override suspend fun deleteTrack(trackId: String, sessionToken: String): Any? {
        TODO("Not yet implemented")
    }
}