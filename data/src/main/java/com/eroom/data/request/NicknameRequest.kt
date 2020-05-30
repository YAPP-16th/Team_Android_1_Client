package com.eroom.data.request

import com.google.gson.annotations.SerializedName

data class NicknameRequest(
    @SerializedName("nickname") var nickname: String
)