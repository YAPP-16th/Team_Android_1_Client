package com.eroom.data.request

import com.google.gson.annotations.SerializedName

data class UIdRequest(
    @SerializedName("uid") var uid: String
)