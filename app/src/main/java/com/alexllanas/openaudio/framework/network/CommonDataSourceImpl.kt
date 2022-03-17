package com.alexllanas.openaudio.framework.network

import com.alexllanas.core.data.remote.common.CommonDataSource
import com.alexllanas.common.responses.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonDataSourceImpl : CommonDataSource {
    @GET("search?format=json")
    override suspend fun search(
        @Query("q") query: String,
    ): SearchResponse
}