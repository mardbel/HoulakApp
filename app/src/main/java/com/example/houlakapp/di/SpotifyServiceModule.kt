package com.example.houlakapp.di

import com.example.houlakapp.data.SearchArtistRepository
import com.example.houlakapp.data.TokenRepository
import com.example.houlakapp.data.remote.AuthenticationService
import com.example.houlakapp.data.remote.SpotifyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://accounts.api.spotify.com/"

@Module
@InstallIn(SingletonComponent::class)
object SpotifyServiceModule {
    @Provides
    @Singleton
    fun provideSpotifyService(): SpotifyService = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpotifyService::class.java)

    @Provides
    @Singleton
    fun provideTokenManager(): TokenRepository = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.AUTHENTICATION_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TokenRepository::class.java)


    @Provides
    @Singleton
    fun provideSearchArtistRepository(): SearchArtistRepository = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SearchArtistRepository::class.java)

    @Provides
    @Singleton
    fun provideAuthenticationService(): AuthenticationService = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.AUTHENTICATION_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthenticationService::class.java)

    @Singleton
    @Provides
    fun providesRetrofitClient(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

}