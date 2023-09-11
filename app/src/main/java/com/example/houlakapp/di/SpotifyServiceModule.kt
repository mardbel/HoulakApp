package com.example.houlakapp.di

import android.app.Application
import com.example.houlakapp.data.SearchArtistRepository
import com.example.houlakapp.data.SearchArtistRepositoryImp
import com.example.houlakapp.data.TokenRepository
import com.example.houlakapp.data.TokenRepositoryImp
import com.example.houlakapp.data.remote.AuthenticationService
import com.example.houlakapp.data.remote.SpotifyService
import com.example.houlakapp.util.SharePreferencesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Module
@InstallIn(SingletonComponent::class)

class SpotifyServiceModule {
    @Provides
    @Singleton
    fun provideSpotifyService(): SpotifyService = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpotifyService::class.java)

    @Provides
    @Singleton
    fun provideTokenManager(
        authenticationService: AuthenticationService,
        sharePreferencesProvider: SharePreferencesProvider
    ): TokenRepository {
        return TokenRepositoryImp(authenticationService, sharePreferencesProvider)
    }

    @Provides
    @Singleton
    fun provideSharePreferences(
        application: Application
    ): SharePreferencesProvider {
        return SharePreferencesProvider(application)
    }


    @Provides
    @Singleton
    fun provideSearchArtistRepository(
            artistService: SpotifyService
    ): SearchArtistRepository {
        return SearchArtistRepositoryImp(artistService)
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(): AuthenticationService = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.AUTHENTICATION_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthenticationService::class.java)

    @Provides
    @Singleton
    fun providesRetrofitClient(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(SpotifyBaseUrls.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

}