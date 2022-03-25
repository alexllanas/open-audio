package com.alexllanas.core.data.remote.home

interface HomeRemoteServiceContract {

    suspend fun search(query: String): Any

}