package com.alexllanas.core.data.remote.user

import com.alexllanas.common.responses.TrackResponse
import com.alexllanas.core.data.mappers.toDomainTrack
import com.alexllanas.core.domain.Track
import com.alexllanas.core.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

/**
 * Processes network responses.
 */
class UserDataSourceImpl(
    private val userRemoteService: UserRemoteService
) : UserDataSource {
    override suspend fun login(email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun logout(sessionToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun registerWithEmail(name: String, email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: String, sessionToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscribers(userId: String, sessionToken: String): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscriptions(userId: String, sessionToken: String): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadAvatar(file: File, sessionToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getTracks(userId: String): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getStream(sessionToken: String): Flow<List<Track>> = flow {
        val stream = userRemoteService
            .getStream(sessionToken)
            .filterIsInstance(TrackResponse::class.java)
            .map { trackResponse ->
                trackResponse.toDomainTrack()
            }
        emit(stream)
    }

    override suspend fun changeName(name: String, sessionToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun changeLocation(location: String, sessionToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun changeBio(bio: String, sessionToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun changeAvatar(filePath: String, sessionToken: String): User {
        TODO("Not yet implemented")
    }

}