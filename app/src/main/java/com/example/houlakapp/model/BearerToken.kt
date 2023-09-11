package com.example.houlakapp.model

import java.time.LocalDateTime

data class BearerToken(
    private val tokenString: String,
    val timeOfCreation: LocalDateTime,
    val secondsUntilExpiration: Int
) {
    val value get() = "Bearer $tokenString"
    override fun toString(): String = "Bearer $tokenString"
}
