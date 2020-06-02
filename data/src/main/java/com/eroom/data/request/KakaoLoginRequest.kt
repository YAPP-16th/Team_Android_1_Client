package com.eroom.data.request

import com.google.gson.annotations.SerializedName

data class KakaoLoginRequest(
    @SerializedName("kakaoId") var kakaoId: String
)