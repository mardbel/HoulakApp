package com.example.houlakapp.data.remote

import com.example.houlakapp.model.Artist
import com.example.houlakapp.model.ArtistResultsResponse
import com.example.houlakapp.model.BearerToken
import com.example.houlakapp.model.TracksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyService {

    @GET("v1/search")
    suspend fun searchArtistByName(
        @Header("Authorization") token: BearerToken,
        @Query("q") artistName: String,
        @Query("type") type: String = "artist",
        @Query("market") market: String = "ES",
        @Query("offset") offset: Int = 0,
    ) : Response<ArtistResultsResponse>

    @GET("v1/artists/{id}")
    suspend fun searchArtistById(
        @Header("Authorization") token: BearerToken,
        @Path("id") id: String,
    ) : Response<Artist>

    @GET("v1/artists/{id}/top-tracks")
    suspend fun getArtistTopTracks(
        @Header("Authorization") token: BearerToken,
        @Path("id") id: String,
        @Query("market") market: String = "ES"
    ): Response<TracksResponse>


}