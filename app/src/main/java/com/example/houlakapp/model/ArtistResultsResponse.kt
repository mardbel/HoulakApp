package com.example.houlakapp.model

data class ArtistResultsResponse(
    val artists: ArtistsContainer
)

data class ArtistsContainer(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<Artist>
)

