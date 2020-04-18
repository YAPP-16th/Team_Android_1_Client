package com.eroom.data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenResponse(
    @JsonProperty("token") var token: String,
    @JsonProperty("refreshToken") var refreshToken: String,
    @JsonProperty("isAdditionalInfoNeeded") var isAdditionalInfoNeeded: Boolean
)