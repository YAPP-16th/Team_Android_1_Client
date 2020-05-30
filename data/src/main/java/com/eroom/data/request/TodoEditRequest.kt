package com.eroom.data.request

import com.google.gson.annotations.SerializedName

data class TodoEditRequest(
    @SerializedName("changedIsEnd") var changedIsEnd: String
)