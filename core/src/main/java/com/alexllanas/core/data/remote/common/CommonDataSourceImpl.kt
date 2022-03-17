package com.alexllanas.core.data.remote.common

import com.alexllanas.core.data.mappers.toDomainPlaylist
import com.alexllanas.core.data.mappers.toDomainTrack
import com.alexllanas.core.data.mappers.toDomainUser
import com.alexllanas.core.domain.Playlist
import com.alexllanas.core.domain.Track
import com.alexllanas.core.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommonDataSourceImpl(
    private val commonRemoteService: CommonRemoteService
) : CommonDataSource {

    override suspend fun search(query: String): Flow<HashMap<String, List<*>>> = flow {
        val response = commonRemoteService.search(query)
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