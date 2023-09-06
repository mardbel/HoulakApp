package com.example.houlakapp.data

import com.example.houlakapp.model.ArtistResultsResponse
import retrofit2.Response

interface SearchArtistRepository {

    suspend fun searchArtistByName(name: String): Response<ArtistResultsResponse>
}