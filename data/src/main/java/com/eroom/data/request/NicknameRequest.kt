package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class NicknameRequest(
    @JsonProperty("nickname") var nickname: String
)