package com.alexllanas.core.data.remote.user

import com.alexllanas.common.responses.TrackResponse

interface UserRemoteService {

    suspend fun getStream(sessionToken: String): List<TrackResponse>

}