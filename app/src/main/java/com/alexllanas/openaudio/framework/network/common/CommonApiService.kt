package com.alexllanas.openaudio.framework.network.common

import com.alexllanas.core.data.remote.common.CommonRemoteServiceContract
import com.alexllanas.openaudio.framework.network.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonApiService : CommonRemoteServiceContract {

    @GET("search?format=json")
    override suspend fun search(
        @Query("q") query: String,
    ): SearchResponse
}

