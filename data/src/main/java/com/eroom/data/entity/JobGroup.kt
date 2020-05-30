package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class JobGroup(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String
)