package com.example.houlakapp.data

import android.util.Base64
import com.example.houlakapp.data.remote.AuthenticationService
import com.example.houlakapp.data.remote.SpotifyService
import com.example.houlakapp.model.*
import retrofit2.Response
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchArtistRepositoryImp @Inject constructor(
    private val artistService: SpotifyService,
    private val authenticationService: AuthenticationService,
    ) : SearchArtistRepository {

    private val clientId = "c8f592110ce64681bd09afb6a8418f9f"
    private val clientSecret = "3aa446d1e15f40d1b05b3250c36dc8d0"

    override suspend fun searchArtistByName(name: String): Response<ArtistResultsResponse> {


        return artistService.searchArtistByName(getToken(), name)
    }

    suspend fun getToken(): BearerToken {

        val authString = "$clientId:$clientSecret"
        val authBytes = authString.toByteArray()
        val authBase64 = Base64.encodeToString(authBytes, Base64.NO_WRAP)


        val token = authenticationService.getNewAccessToken("Basic $authBase64")
        return BearerToken(token.accessToken, LocalDateTime.now(), token.secondsUntilExpiration)

    }


}