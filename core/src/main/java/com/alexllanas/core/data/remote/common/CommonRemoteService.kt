package com.alexllanas.core.data.remote.common

import com.alexllanas.common.responses.NetworkResponse

interface CommonRemoteService {

    suspend fun search(query: String) : NetworkResponse

}