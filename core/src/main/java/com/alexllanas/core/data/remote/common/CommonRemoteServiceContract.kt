package com.alexllanas.core.data.remote.common

interface CommonRemoteServiceContract {

    suspend fun search(query: String): Any

}