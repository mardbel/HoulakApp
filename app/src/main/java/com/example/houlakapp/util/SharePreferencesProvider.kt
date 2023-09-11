package com.example.houlakapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.houlakapp.di.SpotifyBaseUrls
import com.example.houlakapp.model.AccessTokenResponse
import com.example.houlakapp.model.BearerToken
import java.time.LocalDateTime

class SharePreferencesProvider(private var context: Context) {

    fun saveToken(value: AccessTokenResponse?) {
        val settings: SharedPreferences = context.getSharedPreferences(
            SpotifyBaseUrls.KEY_TOKEN, 0
        )
        val editor = settings.edit()
        editor.putString("tokenString", value?.accessToken)
        editor.putInt("secondsUntilExpiration", value?.secondsUntilExpiration!!)
        editor.apply()
    }

    fun getSavedToken(): BearerToken {
        val settings: SharedPreferences = context.getSharedPreferences(
            SpotifyBaseUrls.KEY_TOKEN, 0
        )
        val tokenString = settings.getString("tokenString", null)
        val secondsUntilExpiration = settings.getInt("secondsUntilExpiration", 0)
        return BearerToken(
            tokenString!!,
            LocalDateTime.now(),
            secondsUntilExpiration
        )
    }
}