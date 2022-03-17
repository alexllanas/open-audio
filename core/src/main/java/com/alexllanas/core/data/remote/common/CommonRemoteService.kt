package com.alexllanas.core.data.remote.common

import com.alexllanas.common.responses.SearchResponse

interface CommonRemoteService {

    suspend fun search(query: String) : SearchResponse

}