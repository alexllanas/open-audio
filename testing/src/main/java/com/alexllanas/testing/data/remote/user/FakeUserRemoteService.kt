package com.alexllanas.testing.data.remote.user

import com.alexllanas.core.data.remote.user.UserRemoteService
import com.alexllanas.core.domain.models.User

class FakeUserRemoteService : UserRemoteService {
    override suspend fun login(email: String, password: String): Any {
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
    ): Any {
        TODO("Not yet implemented")
    }

    override suspend fun getStream(sessionToken: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: String, sessionToken: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun getTracks(userId: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscribers(userId: String, sessionToken: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscriptions(userId: String, sessionToken: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun changeName(name: String, sessionToken: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun changeLocation(location: String, sessionToken: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun changeBio(bio: String, sessionToken: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun changeAvatar(filePath: String, sessionToken: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun uploadAvatar(file: Any, sessionToken: String) {
        TODO("Not yet implemented")
    }
}