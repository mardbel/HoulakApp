package com.example.houlakapp.data

import com.example.houlakapp.data.remote.AuthenticationService
import android.util.Base64
import com.example.houlakapp.di.SpotifyBaseUrls
import com.example.houlakapp.util.SharePreferencesProvider
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TokenRepositoryImp @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val sharePreferencesProvider: SharePreferencesProvider,
) : TokenRepository {

    private val clientId = "c8f592110ce64681bd09afb6a8418f9f"
    private val clientSecret = "3aa446d1e15f40d1b05b3250c36dc8d0"

    override suspend fun getToken(): TokenRepository.AuthenticationResult {

        val authString = "$clientId:$clientSecret"
        val authBytes = authString.toByteArray()
        val authBase64 = Base64.encodeToString(authBytes, Base64.NO_WRAP)

        return try {
            val response = authenticationService.getNewAccessToken("Basic $authBase64")
            if (response.isSuccessful) {
                sharePreferencesProvider.saveToken(response.body())
                TokenRepository.AuthenticationResult.Success(response.body()!!)
            } else TokenRepository.AuthenticationResult.NoToken
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> TokenRepository.AuthenticationResult.NetworkError
                is HttpException -> when (throwable.code()) {
                    in 400..499 -> TokenRepository.AuthenticationResult.BadRequest
                    in 500..599 -> TokenRepository.AuthenticationResult.ApiError
                    else -> TokenRepository.AuthenticationResult.GenericError(throwable)
                }
                else -> TokenRepository.AuthenticationResult.GenericError(throwable)
            }

        }
    }
}