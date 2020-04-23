package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoLoginRequest(
    @JsonProperty("kakaoId") var kakaoId: String
)