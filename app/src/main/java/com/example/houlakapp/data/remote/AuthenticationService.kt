package com.example.houlakapp.data.remote

import com.example.houlakapp.data.remote.AuthenticationService.Companion.Resource.API_TOKEN_ENDPOINT
import com.example.houlakapp.data.remote.AuthenticationService.Companion.Resource.defaultGrantType
import com.example.houlakapp.model.AccessTokenResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationService {

    companion object {
        object Resource {
            const val API_TOKEN_ENDPOINT = "api/token"
            const val defaultGrantType = "client_credentials"
        }
    }
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(API_TOKEN_ENDPOINT)
    suspend fun getNewAccessToken(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String = defaultGrantType,
    ): Response<AccessTokenResponse>
}