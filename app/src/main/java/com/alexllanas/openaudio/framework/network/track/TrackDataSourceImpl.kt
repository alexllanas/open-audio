package com.alexllanas.openaudio.framework.network.track

import android.util.Log
import arrow.core.Either
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.TrackMetadata
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.core.util.getResult
import com.alexllanas.openaudio.framework.mappers.toDomainTrack
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import java.lang.IllegalArgumentException

@OptIn(FlowPreview::class)
class TrackDataSourceImpl(
    private val trackApiService: TrackApiService
) : TrackDataSource {
    override suspend fun addTrack(
        title: String,
        mediaUrl: String,
        image: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> =
        suspend {
            trackApiService
                .addTrack(title, mediaUrl, image, sessionToken)
                .toDomainTrack()
        }.asFlow().getResult()

    override suspend fun addTrackToPlaylist(
        id: String,
        title: String,
        mediaUrl: String,
        image: String,
        playlistName: String,
        playListId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Track>> =
        suspend {
            trackApiService
                .addTrackToPlaylist(
                    id,
                    title,
                    mediaUrl,
                    image,
                    playlistName,
                    playListId,
                    sessionToken
                )
                .toDomainTrack()
        }.asFlow()
            .getResult()

    override suspend fun getTrackMetadata(url: String): Flow<TrackMetadata> =
        flowOf(trackApiService.getTrackMetadata(url))

    override suspend fun deleteTrack(
        trackId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>> =
        suspend {
            val track = trackApiService
                .deleteTrack(
                    trackId,
                    sessionToken
                )?.toDomainTrack()
            track == null
        }.asFlow().getResult()
}