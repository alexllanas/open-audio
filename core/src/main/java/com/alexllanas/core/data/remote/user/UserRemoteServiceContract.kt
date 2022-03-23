package com.alexllanas.core.data.remote.user


interface UserRemoteServiceContract {

    suspend fun login(email: String, password: String): Any

    suspend fun logout(sessionToken: String)

    suspend fun registerWithEmail(name: String, email: String, password: String): Any

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        sessionToken: String
    ): Any

    suspend fun getStream(sessionToken: String): List<Any>

    suspend fun getUserById(userId: String, sessionToken: String): Any

    suspend fun getTracks(userId: String): List<Any>

    suspend fun getSubscribers(userId: String, sessionToken: String): List<Any>

    suspend fun getSubscriptions(userId: String, sessionToken: String): List<Any>

    suspend fun changeName(name: String, sessionToken: String): Any

    suspend fun changeLocation(location: String, sessionToken: String): Any

    suspend fun changeBio(bio: String, sessionToken: String): Any

    suspend fun changeAvatar(filePath: String, sessionToken: String): Any

//    suspend fun uploadAvatar(file: Any, sessionToken: String)

}

