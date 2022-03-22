package com.alexllanas.openaudio.framework.network.common

import com.alexllanas.core.data.remote.common.CommonRemoteService
import com.alexllanas.openaudio.framework.network.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonApiService : CommonRemoteService {

    @GET("search?format=json")
    override suspend fun search(
        @Query("q") query: String,
    ): SearchResponse
}