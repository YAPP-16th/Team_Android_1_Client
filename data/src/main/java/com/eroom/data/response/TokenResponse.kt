package com.eroom.data.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token") var token: String,
    @SerializedName("refreshToken") var refreshToken: String,
    @SerializedName("isAdditionalInfoNeeded") var isAdditionalInfoNeeded: Boolean
)