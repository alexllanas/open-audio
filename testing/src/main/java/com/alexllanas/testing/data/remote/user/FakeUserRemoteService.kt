package com.alexllanas.testing.data.remote.user

import com.alexllanas.core.data.remote.user.UserRemoteServiceContract
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import java.io.File

class FakeUserRemoteService : UserRemoteServiceContract {
    override suspend fun toggleLike(trackId: String, sessionToken: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun logout(sessionToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun registerWithEmail(
        name: String,
        email: String,
        password: String,
        ajax: String
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): User {
        TODO("Not yet implemented")
    }

    override suspend fun getStream(sessionToken: String): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: String, sessionToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getTracks(userId: String): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowers(userId: String, sessionToken: String): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowing(userId: String, sessionToken: String): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun followUser(userId: String, sessionToken: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun unfollowUser(userId: String, sessionToken: String): Any {
        TODO("Not yet implemented")
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

    suspend fun uploadAvatar(file: File, sessionToken: String) {
        TODO("Not yet implemented")
    }
}