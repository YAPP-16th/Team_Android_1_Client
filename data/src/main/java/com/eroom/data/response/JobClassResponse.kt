package com.eroom.data.response

import com.google.gson.annotations.SerializedName

data class JobClassResponse(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("jobGroupId") var jobGroupId: Long
)