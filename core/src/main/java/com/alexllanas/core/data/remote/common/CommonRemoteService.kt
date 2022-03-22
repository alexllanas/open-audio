package com.alexllanas.core.data.remote.common

interface CommonRemoteService {

    suspend fun search(query: String) : Any

}