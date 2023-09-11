package com.example.houlakapp.data

import com.example.houlakapp.data.remote.SpotifyService
import com.example.houlakapp.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchArtistRepositoryImp @Inject constructor(
    private val artistService: SpotifyService,
    ) : SearchArtistRepository {

    override suspend fun searchArtistByName(name: String, token: BearerToken): Flow<SearchArtistRepository.ArtistResult<ArtistResultsResponse?>> {

        return try {
            val response = artistService.searchArtistByName(token, name)
            if (response.isSuccessful) {
                flowOf(SearchArtistRepository.ArtistResult.Success(response.body()!!))
            } else flowOf(SearchArtistRepository.ArtistResult.NetworkError)
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> flowOf(SearchArtistRepository.ArtistResult.NetworkError)
                is HttpException -> when (throwable.code()) {
                    400 -> flowOf(SearchArtistRepository.ArtistResult.BadRequest)
                    401 -> flowOf(SearchArtistRepository.ArtistResult.ExpiredToken)
                    in 402..499 -> flowOf(SearchArtistRepository.ArtistResult.BadRequest)
                    in 500..599 -> flowOf(SearchArtistRepository.ArtistResult.ApiError)
                    else -> flowOf(SearchArtistRepository.ArtistResult.GenericError(throwable))
                }
                else -> flowOf(SearchArtistRepository.ArtistResult.GenericError(throwable))
            }
        }
    }

    override suspend fun searchArtistById(id: String, token: BearerToken): Flow<SearchArtistRepository.ArtistResult<Artist?>> {
        return try {
            val response = artistService.searchArtistById(token, id)
            if (response.isSuccessful) {
                flowOf(SearchArtistRepository.ArtistResult.Success(response.body()!!))
            } else flowOf(SearchArtistRepository.ArtistResult.NetworkError)
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> flowOf(SearchArtistRepository.ArtistResult.NetworkError)
                is HttpException -> when (throwable.code()) {
                    400 -> flowOf(SearchArtistRepository.ArtistResult.BadRequest)
                    401 -> flowOf(SearchArtistRepository.ArtistResult.ExpiredToken)
                    in 402..499 -> flowOf(SearchArtistRepository.ArtistResult.BadRequest)
                    in 500..599 -> flowOf(SearchArtistRepository.ArtistResult.ApiError)
                    else -> flowOf(SearchArtistRepository.ArtistResult.GenericError(throwable))
                }
                else -> flowOf(SearchArtistRepository.ArtistResult.GenericError(throwable))
            }
        }
    }

    override suspend fun getTopTracks(id: String, token: BearerToken): Flow<SearchArtistRepository.ArtistResult<TracksResponse?>> {
        return try {
            val response = artistService.getArtistTopTracks(token, id)
            if (response.isSuccessful) {
                flowOf(SearchArtistRepository.ArtistResult.Success(response.body()!!))
            } else flowOf(SearchArtistRepository.ArtistResult.NetworkError)
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> flowOf(SearchArtistRepository.ArtistResult.NetworkError)
                is HttpException -> when (throwable.code()) {
                    400 -> flowOf(SearchArtistRepository.ArtistResult.BadRequest)
                    401 -> flowOf(SearchArtistRepository.ArtistResult.ExpiredToken)
                    in 402..499 -> flowOf(SearchArtistRepository.ArtistResult.BadRequest)
                    in 500..599 -> flowOf(SearchArtistRepository.ArtistResult.ApiError)
                    else -> flowOf(SearchArtistRepository.ArtistResult.GenericError(throwable))
                }
                else -> flowOf(SearchArtistRepository.ArtistResult.GenericError(throwable))
            }
        }
    }

}