package com.example.houlakapp.model

data class Artist(
    val genres: List<String>,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int
)
