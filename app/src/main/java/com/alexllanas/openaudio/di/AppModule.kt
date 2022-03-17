package com.alexllanas.openaudio.di

import android.content.Context
import com.alexllanas.core.data.remote.common.CommonDataSource
import com.alexllanas.core.data.remote.common.CommonDataSourceImpl
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.data.remote.user.UserDataSourceImpl
import com.alexllanas.core.data.remote.user.UserRemoteService
import com.alexllanas.core.interactors.search.Search
import com.alexllanas.core.interactors.stream.GetStream
import com.alexllanas.openaudio.framework.network.UserApiService
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

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        @ApplicationContext app: Context
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
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
            .baseUrl("https://openwhyd.org")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(
                gsonConverterFactory
            )
    }

    @Singleton
    @Provides
    fun provideCommonDataSource(retrofit: Retrofit.Builder): CommonDataSource {
        return retrofit.build().create(CommonDataSourceImpl::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApiService(retrofit: Retrofit.Builder): UserApiService {
        return retrofit.build().create(UserApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDataSource(userApiService: UserApiService): UserDataSource {
        return UserDataSourceImpl(userApiService)
    }

    @Singleton
    @Provides
    fun provideGetStream(
        userDataSource: UserDataSource,
    ) = GetStream(userDataSource)

    @Singleton
    @Provides
    fun provideSearch(
        commonDataSource: CommonDataSource,
        @ApplicationContext context: Context
    ) = Search(commonDataSource)
}