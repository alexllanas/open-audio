package com.alexllanas.openaudio.framework.network

import com.alexllanas.openaudio.framework.network.models.HtmlResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface YoutubeApi {

    @GET("watch?v=xo4lRv51Hf0")
    suspend fun getVideo(): String
}