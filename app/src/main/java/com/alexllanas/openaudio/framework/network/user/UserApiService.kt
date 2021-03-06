package com.alexllanas.openaudio.framework.network.user

import com.alexllanas.core.data.remote.user.UserRemoteServiceContract
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants.Companion.PAGE_LIMIT
import com.alexllanas.openaudio.framework.network.models.*
import okhttp3.MultipartBody
import retrofit2.http.*
import java.io.File


interface UserApiService :
    UserRemoteServiceContract {

    @GET("/api/post?action=toggleLovePost")
    override suspend fun toggleLike(
        @Query("pId") trackId: String,
        @Header("Cookie") sessionToken: String
    ): PostResponse

    @GET("login?action=login&ajax=true&includeUser=true")
    override suspend fun login(
        @Query("email", encoded = true) email: String,
        @Query("password", encoded = true) password: String
    ): LoginResponse

    @GET("/login?action=logout&ajax=true")
    override suspend fun logout(@Header("Cookie") sessionToken: String)

    /**
     * TODO: Get this working via postman first
     */
    @FormUrlEncoded
    @GET("/register")
    override suspend fun registerWithEmail(
        @Field("name") name: String,
        @Field("name", encoded = true) email: String,
        @Field("name", encoded = true) password: String,
        @Field("ajax") ajax: String
    ): UserResponse

    @GET("/api/user")
    override suspend fun changePassword(
        @Query("oldPwd", encoded = true) currentPassword: String,
        @Query("pwd", encoded = true) newPassword: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("?limit=${PAGE_LIMIT}&format=json")
    override suspend fun getStream(@Header("Cookie") sessionToken: String): List<TrackResponse>

    @GET("/api/user?isSubscr=true&includeSubscr=true")
    override suspend fun getUser(
        @Query("id") userId: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("/api/follow?action=insert")
    override suspend fun followUser(
        @Query("tId") userId: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("/api/follow?action=delete")
    override suspend fun unfollowUser(
        @Query("tId") userId: String,
        @Header("Cookie") sessionToken: String
    ): UserResponse

    @GET("/u/{id}?format=json")
    override suspend fun getTracks(@Path("id", encoded = true) userId: String): List<TrackResponse>

    @GET("/api/follow/fetchFollowers?isSubscr=true")
    override suspend fun getFollowers(
        @Query("id") userId: String,
        @Header("Cookie") sessionToken: String
    ): List<UserResponse>

    @GET("/api/follow/fetchFollowing?isSubscr=true")
    override suspend fun getFollowing(
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

    @Multipart
    @POST("/upload")
    suspend fun uploadAvatar(
        @Part file: MultipartBody.Part,
        @Header("Cookie") sessionToken: String
    ): UploadResponse
}