package com.alexllanas.openaudio.framework.network

import com.alexllanas.core.data.remote.user.UserRemoteService
import com.alexllanas.common.responses.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApiService : UserRemoteService {

    @GET("format=json")
    override suspend fun getStream(@Header("Cookie") sessionToken: String): List<TrackResponse>

}