package com.alexllanas.core.data.remote.user

import com.alexllanas.common.responses.NetworkResponse

interface UserRemoteService {

    suspend fun getStream(sessionToken: String): List<NetworkResponse>

}