package com.alexllanas.core.data.remote.common

import com.alexllanas.core.data.remote.NetworkResponse

interface CommonRemoteService {

    suspend fun search(query: String) : NetworkResponse

}