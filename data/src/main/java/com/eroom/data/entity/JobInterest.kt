package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class JobInterest(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("jobGroupId") var jobGroupId: Long
)