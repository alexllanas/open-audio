package com.alexllanas.openaudio.framework.network

import com.alexllanas.core.data.remote.NetworkResponse
import com.alexllanas.core.data.remote.common.CommonDataSource
import com.alexllanas.core.data.remote.common.CommonRemoteService
import com.alexllanas.core.domain.Playlist
import com.alexllanas.core.domain.Track
import com.alexllanas.core.domain.User
import com.alexllanas.openaudio.framework.mappers.toDomainPlaylist
import com.alexllanas.openaudio.framework.mappers.toDomainTrack
import com.alexllanas.openaudio.framework.mappers.toDomainUser
import com.alexllanas.openaudio.framework.network.responses.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.IllegalArgumentException

class CommonDataSourceImpl(
    private val commonRemoteService: CommonRemoteService
) : CommonDataSource {

    override suspend fun search(query: String): Flow<HashMap<String, List<*>>> = flow {
        val response = commonRemoteService.search(query)
        require(response is SearchResponse) { "Illegal network response type." }

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
    }
}