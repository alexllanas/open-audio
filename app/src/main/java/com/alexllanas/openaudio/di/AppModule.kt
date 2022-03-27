package com.alexllanas.openaudio.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alexllanas.core.data.remote.home.HomeDataSource
import com.alexllanas.core.data.remote.playlist.PlaylistDataSource
import com.alexllanas.core.data.remote.track.TrackDataSource
import com.alexllanas.openaudio.framework.network.home.HomeDataSourceImpl
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.interactors.auth.AuthInteractors
import com.alexllanas.core.interactors.auth.Login
import com.alexllanas.core.interactors.auth.RegisterWithEmail
import com.alexllanas.core.interactors.common.*
import com.alexllanas.core.interactors.home.*
import com.alexllanas.core.interactors.profile.*
import com.alexllanas.core.interactors.common.AddTrack
import com.alexllanas.core.interactors.common.AddTrackToPlaylist
import com.alexllanas.core.interactors.upload.UploadInteractors
import com.alexllanas.openaudio.framework.network.user.UserDataSourceImpl
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.Constants.Companion.BASE_URL
import com.alexllanas.openaudio.framework.local.AppDatabase
import com.alexllanas.openaudio.framework.local.PlaylistDao
import com.alexllanas.openaudio.framework.local.TrackDao
import com.alexllanas.openaudio.framework.local.UserDao
import com.alexllanas.openaudio.framework.network.ResponseInterceptor
import com.alexllanas.openaudio.framework.network.home.HomeApiService
import com.alexllanas.openaudio.framework.network.playlist.PlaylistApiService
import com.alexllanas.openaudio.framework.network.playlist.PlaylistDataSourceImpl
import com.alexllanas.openaudio.framework.network.track.TrackApiService
import com.alexllanas.openaudio.framework.network.track.TrackDataSourceImpl
import com.alexllanas.openaudio.framework.network.user.UserApiService
import com.alexllanas.openaudio.presentation.MainApplication
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    /**
     * Database
     */
    @Singleton
    @Provides
    fun provideRoomDatabase(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTrackDao(db: AppDatabase): TrackDao {
        return db.trackDao()
    }

    @Singleton
    @Provides
    fun providePlaylistDao(db: AppDatabase): PlaylistDao {
        return db.playlistDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    /**
     * Network
     */
    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        @ApplicationContext app: Context
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().addInterceptor(ResponseInterceptor())
    }

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        val gson = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        okHttpClientBuilder: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(
                gsonConverterFactory
            )
    }

    @Singleton
    @Provides
    fun provideUserApiService(retrofit: Retrofit.Builder): UserApiService {
        return retrofit.build().create(UserApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTrackApiService(retrofit: Retrofit.Builder): TrackApiService {
        return retrofit.build().create(TrackApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePlaylistApiService(retrofit: Retrofit.Builder): PlaylistApiService {
        return retrofit.build().create(PlaylistApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCommonApiService(retrofit: Retrofit.Builder): HomeApiService {
        return retrofit.build().create(HomeApiService::class.java)
    }


    // binds
    @Singleton
    @Provides
    fun provideUserDataSource(userApiService: UserApiService): UserDataSource {
        return UserDataSourceImpl(userApiService)
    }

    // binds
    @Singleton
    @Provides
    fun provideTrackDataSource(trackApiService: TrackApiService): TrackDataSource {
        return TrackDataSourceImpl(trackApiService)
    }

    // binds
    @Singleton
    @Provides
    fun providePlaylistDataSource(playlistApiService: PlaylistApiService): PlaylistDataSource {
        return PlaylistDataSourceImpl(playlistApiService)
    }

    // binds
    @Singleton
    @Provides
    fun provideHomeDataSource(homeApiService: HomeApiService): HomeDataSource {
        return HomeDataSourceImpl(homeApiService)
    }

    /**
     * Use Cases
     */
    @Singleton
    @Provides
    fun provideHomeInteractors(
        userDataSource: UserDataSource,
        trackDataSource: TrackDataSource,
        playlistDataSource: PlaylistDataSource,
        homeDataSource: HomeDataSource
    ): HomeInteractors {
        return HomeInteractors(
            GetFollowers(userDataSource),
            GetFollowing(userDataSource),
            GetPlaylist(playlistDataSource),
            GetPlaylistTracks(playlistDataSource),
            GetStream(userDataSource),
            GetUser(userDataSource),
            GetUserTracks(userDataSource),
            LikeTrack(trackDataSource),
            UnlikeTrack(trackDataSource),
            Search(homeDataSource),
            FollowUser(userDataSource),
            UnfollowUser(userDataSource),
            AddTrackToPlaylist(trackDataSource)
        )
    }

    @Singleton
    @Provides
    fun provideAuthInteractors(
        userDataSource: UserDataSource
    ): AuthInteractors {
        return AuthInteractors(
            Login(userDataSource),
            RegisterWithEmail(userDataSource)
        )
    }

    @Singleton
    @Provides
    fun provideProfileInteractors(
        userDataSource: UserDataSource,
        trackDataSource: TrackDataSource
    ): ProfileInteractors {
        return ProfileInteractors(
            Logout(userDataSource),
            GetUser(userDataSource),
            GetFollowers(userDataSource),
            GetFollowing(userDataSource),
            ChangePassword(userDataSource),
            ChangeName(userDataSource),
            ChangeBio(userDataSource),
            ChangeLocation(userDataSource),
            ChangeAvatar(userDataSource),
            FollowUser(userDataSource),
            UnfollowUser(userDataSource),
            LikeTrack(trackDataSource),
            UnlikeTrack(trackDataSource)
        )
    }

    @Singleton
    @Provides
    fun provideUploadInteractors(
        trackDataSource: TrackDataSource
    ): UploadInteractors {
        return UploadInteractors(
            AddTrack(trackDataSource),
            AddTrackToPlaylist(trackDataSource),
            LikeTrack(trackDataSource),
            UnlikeTrack(trackDataSource)
        )
    }

}