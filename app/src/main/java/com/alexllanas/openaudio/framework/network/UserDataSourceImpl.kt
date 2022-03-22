package com.alexllanas.openaudio.framework.network

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.data.remote.user.UserRemoteService
import com.alexllanas.openaudio.framework.mappers.toDomainTrack
import com.alexllanas.core.domain.Track
import com.alexllanas.core.domain.User
import com.alexllanas.openaudio.framework.network.responses.TrackResponse
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
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

    @OptIn(FlowPreview::class)
    override suspend fun getStream(sessionToken: String): Flow<Either<Throwable, List<Track>>> =
        suspend {
            userRemoteService
                .getStream(sessionToken).filterIsInstance<TrackResponse>()
                .map { it.toDomainTrack() }
        }.asFlow()
            .map { it.right() }
            .catch { it.left() }

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