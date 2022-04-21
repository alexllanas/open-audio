package com.alexllanas.core.data.remote.user

import arrow.core.Either
import com.alexllanas.core.domain.models.Post
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun toggleLike(trackId: String, sessionToken: String):  Flow<Either<Throwable, Post>>

    suspend fun login(email: String, password: String): Flow<Either<Throwable, User>>

    suspend fun logout(sessionToken: String)

    suspend fun registerWithEmail(
        name: String,
        email: String,
        password: String
    ): Flow<Either<Throwable, User>>

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>>

    suspend fun getUser(userId: String, sessionToken: String): Flow<Either<Throwable, User>>

    suspend fun getFollowers(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, List<User>>>

    suspend fun getFollowing(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, List<User>>>

    suspend fun followUser(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>>

    suspend fun unfollowUser(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>>

    suspend fun uploadAvatar(
        filePath: String,
        sessionToken: String
    ): Flow<String>

    suspend fun getTracks(userId: String): Flow<Either<Throwable, List<Track>>>

    suspend fun getStream(sessionToken: String): Flow<Either<Throwable, List<Track>>>

    suspend fun changeName(name: String, sessionToken: String): Flow<Either<Throwable, User>>

    suspend fun changeLocation(
        location: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>>

    suspend fun changeBio(bio: String, sessionToken: String): Flow<Either<Throwable, User>>

    suspend fun changeAvatar(
        filePath: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>>


}