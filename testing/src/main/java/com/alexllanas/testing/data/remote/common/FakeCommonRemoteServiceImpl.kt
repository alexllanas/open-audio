package com.alexllanas.testing.data.remote.common

import com.alexllanas.core.data.remote.common.CommonRemoteService
import com.alexllanas.testing.*
import com.alexllanas.testing.NETWORK_ERROR_MESSAGE
import com.alexllanas.testing.PLAYLISTS
import com.alexllanas.testing.POSTS
import com.alexllanas.testing.TRACKS
import com.alexllanas.testing.USERS
import java.lang.Exception

class FakeCommonRemoteServiceImpl : CommonRemoteService {
    override suspend fun search(query: String): HashMap<String, List<*>> {
        if (query == "succeed") {
            val resultMap = hashMapOf<String, List<*>>()
            resultMap["tracks"] = TRACKS
            resultMap["playlists"] = PLAYLISTS
            resultMap["users"] = USERS
            resultMap["posts"] = POSTS
            return resultMap
        } else {
            throw Exception(NETWORK_ERROR_MESSAGE)
        }
    }
}