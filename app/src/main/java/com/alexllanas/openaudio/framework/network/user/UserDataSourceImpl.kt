package com.alexllanas.openaudio.framework.network.user

import arrow.core.Either
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.getResult
import com.alexllanas.openaudio.framework.mappers.toDomainTrack
import com.alexllanas.openaudio.framework.mappers.toDomainUser
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Processes network responses.
 */
@OptIn(FlowPreview::class)
class UserDataSourceImpl(
    private val userApiService: UserApiService
) : UserDataSource {
    override suspend fun login(email: String, password: String): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .login(email, password)
                .user!!.toDomainUser()
        }.asFlow().getResult()

    override suspend fun logout(sessionToken: String) = userApiService.logout(sessionToken)

    override suspend fun registerWithEmail(
        name: String,
        email: String,
        password: String
    ): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .registerWithEmail(name, email, password)
                .toDomainUser()
        }.asFlow().getResult()

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .changePassword(currentPassword, newPassword, sessionToken)
                .toDomainUser()
        }.asFlow().getResult()

    override suspend fun getUser(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .getUser(userId, sessionToken)
                .toDomainUser()
        }.asFlow().getResult()

    override suspend fun getFollowers(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, List<User>>> =
        suspend {
            userApiService
                .getFollowers(userId, sessionToken)
                .map { it.toDomainUser() }
        }.asFlow().getResult()


    override suspend fun getFollowing(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, List<User>>> =
        suspend {
            userApiService
                .getFollowing(userId, sessionToken)
                .map { it.toDomainUser() }
        }.asFlow().getResult()

    override suspend fun followUser(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>> =
        suspend {
            val user = userApiService
                .followUser(userId, sessionToken).toDomainUser()
            user.id != null
        }.asFlow().getResult()

    override suspend fun unfollowUser(
        userId: String,
        sessionToken: String
    ): Flow<Either<Throwable, Boolean>> =
        suspend {
            val user = userApiService
                .unfollowUser(userId, sessionToken)
                .toDomainUser()
            user.id == null
        }.asFlow().getResult()

    override suspend fun uploadAvatar(
        filePath: String,
        sessionToken: String
    ): Flow<String> =
        suspend {
            val file = File(filePath)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            userApiService
                .uploadAvatar(body, sessionToken)
                .file.path ?: throw Error("Something went wrong while uploading image.")
        }.asFlow()

    override suspend fun getTracks(userId: String): Flow<Either<Throwable, List<Track>>> =
        suspend {
            userApiService
                .getTracks(userId)
                .map {
                    it.toDomainTrack()
                }
        }.asFlow().getResult()

    override suspend fun getStream(sessionToken: String): Flow<Either<Throwable, List<Track>>> =
        suspend {
            userApiService
                .getStream(sessionToken)
                .map {
                    it.toDomainTrack()
                }
        }.asFlow().getResult()

    override suspend fun changeName(
        name: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .changeName(name, sessionToken)
                .toDomainUser()
        }.asFlow().getResult()

    override suspend fun changeLocation(
        location: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .changeLocation(location, sessionToken)
                .toDomainUser()
        }.asFlow().getResult()

    override suspend fun changeBio(
        bio: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .changeBio(bio, sessionToken)
                .toDomainUser()
        }.asFlow().getResult()

    override suspend fun changeAvatar(
        filePath: String,
        sessionToken: String
    ): Flow<Either<Throwable, User>> =
        suspend {
            userApiService
                .changeAvatar(filePath, sessionToken)
                .toDomainUser()
        }.asFlow().getResult()


}