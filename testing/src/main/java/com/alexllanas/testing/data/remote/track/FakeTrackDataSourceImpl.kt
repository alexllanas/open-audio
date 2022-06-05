package com.alexllanas.testing.data.remote.track

import arrow.core.Either
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.TrackMetadata
import com.alexllanas.core.util.getResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeTrackDataSourceImpl(private val fakeTrackRemoteServiceImpl: FakeTrackRemoteServiceImpl) :
    TrackDataSource {
    override suspend fun addTrack(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTrackToPlaylist(
        id: String,
        title: String,
        mediaUrl: String,
        image: String,
        playlistName: String,
        playListId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrackMetadata(videoId: String): Flow<TrackMetadata> =
        flowOf(fakeTrackRemoteServiceImpl.getTrackMetadata(videoId))


    override suspend fun deleteTrack(
        trackId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>> {
        TODO("Not yet implemented")
    }
}