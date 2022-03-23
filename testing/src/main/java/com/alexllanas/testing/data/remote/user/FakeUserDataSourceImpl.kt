package com.alexllanas.testing.data.remote.user

import arrow.core.Either
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow

class FakeUserDataSourceImpl : UserDataSource {
    override suspend fun login(email: String, password: String): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(sessionToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun registerWithEmail(
        name: String,
        email: String,
        password: String
    ): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscribers(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, List<User>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscriptions(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, List<User>>> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadAvatar(
        file: Any,
        sessionToken: String
    ): Flow<Either<Throwable, String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTracks(userId: String): Flow<Either<Throwable, List<Track>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStream(sessionToken: String): Flow<Either<Throwable, List<Track>>> {
        TODO("Not yet implemented")
    }

    override suspend fun changeName(
        name: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

    override suspend fun changeLocation(
        location: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

    override suspend fun changeBio(
        bio: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

    override suspend fun changeAvatar(
        filePath: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> {
        TODO("Not yet implemented")
    }

}