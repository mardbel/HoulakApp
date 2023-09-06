package com.example.houlakapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccessTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val secondsUntilExpiration: Int,
    @SerializedName("token_type") val tokenType: String
) : Parcelable
