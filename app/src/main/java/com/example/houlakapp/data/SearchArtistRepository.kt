package com.example.houlakapp.data

import com.example.houlakapp.model.Artist
import com.example.houlakapp.model.ArtistResultsResponse
import com.example.houlakapp.model.BearerToken
import com.example.houlakapp.model.TracksResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchArtistRepository {

    suspend fun searchArtistByName(name: String, token: BearerToken): Flow<ArtistResult<ArtistResultsResponse?>>

    sealed class ArtistResult<out T> {
        class GenericError(val cause: Throwable) : ArtistResult<Nothing?>()
        class Success<out T>(val data: T): ArtistResult<T>()
        object ExpiredToken : ArtistResult<Nothing?>()
        object NetworkError : ArtistResult<Nothing?>()
        object ApiError : ArtistResult<Nothing?>()
        object BadRequest : ArtistResult<Nothing?>()
    }

    suspend fun searchArtistById(id: String, token: BearerToken): Flow<ArtistResult<Artist?>>

    suspend fun getTopTracks(id: String, token: BearerToken): Flow<ArtistResult<TracksResponse?>>

}