package com.alexllanas.openaudio.framework.network.home

import com.alexllanas.core.data.remote.home.HomeRemoteServiceContract
import com.alexllanas.openaudio.framework.network.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService : HomeRemoteServiceContract {

    @GET("search?limit=${Int.MAX_VALUE}&format=json")
    override suspend fun search(
        @Query("q") query: String,
    ): SearchResponse
}

