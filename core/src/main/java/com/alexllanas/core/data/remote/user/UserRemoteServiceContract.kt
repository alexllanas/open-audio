package com.alexllanas.core.data.remote.user

import arrow.core.Either
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow


interface UserRemoteServiceContract {

    suspend fun toggleLike(trackId: String, sessionToken: String): Any

    suspend fun login(email: String, password: String): Any

    suspend fun logout(sessionToken: String)

    suspend fun registerWithEmail(
        name: String,
        email: String,
        password: String,
        ajax: String = "true"
    ): Any

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): Any

    suspend fun getStream(sessionToken: String): List<Any>

    suspend fun getUser(userId: String, sessionToken: String): Any

    suspend fun getTracks(userId: String): List<Any>

    suspend fun getFollowers(userId: String, sessionToken: String): List<Any>

    suspend fun getFollowing(userId: String, sessionToken: String): List<Any>


    suspend fun followUser(
        userId: String,
        sessionToken: String
    ): Any

    suspend fun unfollowUser(
        userId: String,
        sessionToken: String
    ): Any

    suspend fun changeName(name: String, sessionToken: String): Any

    suspend fun changeLocation(location: String, sessionToken: String): Any

    suspend fun changeBio(bio: String, sessionToken: String): Any

    suspend fun changeAvatar(filePath: String, sessionToken: String): Any

//    suspend fun uploadAvatar(file: Any, sessionToken: String) : Any

}

