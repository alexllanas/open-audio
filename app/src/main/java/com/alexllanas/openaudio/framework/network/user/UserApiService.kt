package com.alexllanas.openaudio.framework.network.user

import com.alexllanas.core.data.remote.user.UserRemoteService
import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.framework.network.models.TrackResponse
import com.alexllanas.openaudio.framework.network.models.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*
import java.io.File

interface UserApiService : UserRemoteService {

    @GET("/login?action=login&ajax=true&includeUser=true")
    override suspend fun login(
        @Query("email", encoded = true) email: String,
        @Query("password", encoded = true) password: String
    ): UserResponse

    @GET("/login?action=logout&ajax=true")
    override suspend fun logout(@Header("Cookie") sessionToken: String)

    @GET("/api/user")
    override suspend fun changePassword(
        @Query("oldPwd", encoded = true) currentPassword: String,
        @Query("pwd", encoded = true) newPassword: String,
        @Header("Cookie") sessionToken: String
    ): Any

    @GET("?format=json")
    override suspend fun getStream(@Header("Cookie") sessionToken: String): List<TrackResponse>

    @GET("/api/user?isSubscr=true&includeSubscr=true")
    override suspend fun getUserById(
        @Query("id") userId: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("/u/{id}?format=json")
    override suspend fun getTracks(@Path("id", encoded = true) userId: String): List<TrackResponse>

    /**
     * Users that follow me.
     */
    @GET("/api/follow/fetchFollowers?isSubscr=true")
    override suspend fun getSubscribers(
        @Query("id") userId: String,
        @Header("Cookie") sessionToken: String
    ): List<UserResponse>

    /**
     * Users I'm following.
     */
    @GET("/api/follow/fetchFollowing?isSubscr=true")
    override suspend fun getSubscriptions(
        @Query("id") userId: String,
        @Header("Cookie") sessionToken: String
    ): List<UserResponse>

    @GET("/api/user")
    override suspend fun changeName(
        @Query("name") name: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("/api/user")
    override suspend fun changeLocation(
        @Query("loc") location: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("/api/user")
    override suspend fun changeBio(
        @Query("bio") bio: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("/api/user")
    override suspend fun changeAvatar(
        @Query("img") filePath: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

//    @Multipart
//    @POST("/upload")
//    override suspend fun uploadAvatar(
//        @Part file: MultipartBody.Part,
//        @Header("Cookie") sessionToken: String
//    )
}