package com.alexllanas.openaudio.framework.network.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.alexllanas.core.data.remote.common.CommonDataSource
import com.alexllanas.core.data.remote.common.CommonRemoteService
import com.alexllanas.core.domain.models.NetworkError
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.framework.mappers.toDomainPlaylist
import com.alexllanas.openaudio.framework.mappers.toDomainTrack
import com.alexllanas.openaudio.framework.mappers.toDomainUser
import com.alexllanas.openaudio.framework.network.models.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CommonDataSourceImpl(
    private val commonRemoteService: CommonApiService
) : CommonDataSource {

    override suspend fun search(query: String): Flow<Either<NetworkError, HashMap<String, List<*>>>> =
        flow {
            val response = commonRemoteService
                .search(query)
            val resultMap = hashMapOf<String, List<*>>()
            resultMap["tracks"] =
                response.results?.tracks?.map { trackResponse -> trackResponse.toDomainTrack() }
                    ?: emptyList<Track>()
            resultMap["playlists"] =
                response.results?.playlists?.map { playlistResponse -> playlistResponse.toDomainPlaylist() }
                    ?: emptyList<Playlist>()
            resultMap["users"] =
                response.results?.users?.map { userResponse -> userResponse.toDomainUser() }
                    ?: emptyList<User>()
            resultMap["posts"] =
                response.results?.posts?.map { trackResponse -> trackResponse.toDomainTrack() }
                    ?: emptyList<Track>()
            emit(resultMap)
        }.map { it.right() }
            .catch { NetworkError(it).left() }

}