package com.alexllanas.core.data.remote.user

import com.alexllanas.core.domain.Track
import com.alexllanas.core.domain.User
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserDataSource {

    suspend fun login(email: String, password: String): User

    suspend fun logout(sessionToken: String)

    suspend fun registerWithEmail(name: String, email: String, password: String): User

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): User

    suspend fun getUserById(userId: String, sessionToken: String): User

    suspend fun getSubscribers(userId: String, sessionToken: String): List<User>

    suspend fun getSubscriptions(userId: String, sessionToken: String): List<User>

    suspend fun uploadAvatar(file: File, sessionToken: String)

    suspend fun getTracks(userId: String): List<Track>

    suspend fun getStream(sessionToken: String): Flow<List<Track>>

    suspend fun changeName(name: String, sessionToken: String): User

    suspend fun changeLocation(location: String, sessionToken: String): User

    suspend fun changeBio(bio: String, sessionToken: String): User

    suspend fun changeAvatar(filePath: String, sessionToken: String): User

}