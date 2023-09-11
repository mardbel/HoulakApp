package com.example.houlakapp.data

import com.example.houlakapp.model.AccessTokenResponse

interface TokenRepository {

    suspend fun getToken(): AuthenticationResult

    sealed class AuthenticationResult {
        object NetworkError : AuthenticationResult()
        object ApiError : AuthenticationResult()
        object BadRequest : AuthenticationResult()
        class GenericError(val cause: Throwable) : AuthenticationResult()
        class Success(val token: AccessTokenResponse) : AuthenticationResult()
        object NoToken : AuthenticationResult()
        object ExpiredToken : AuthenticationResult()
    }

}