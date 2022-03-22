package com.alexllanas.core.data.remote.user

import com.alexllanas.core.data.remote.NetworkResponse

interface UserRemoteService {

    suspend fun getStream(sessionToken: String): List<NetworkResponse>

}