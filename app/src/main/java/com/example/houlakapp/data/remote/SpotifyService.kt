package com.example.houlakapp.data.remote

import com.example.houlakapp.model.ArtistResultsResponse
import com.example.houlakapp.model.BearerToken
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyService {

    @GET("v1/search")
    suspend fun searchArtistByName(
        @Header("Authorization") token: BearerToken,
        @Query("q") artistName: String,
        @Query("type") type: String = "artist",
        @Query("market") market: String = "ES",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ) : Response<ArtistResultsResponse>
}