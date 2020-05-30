package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class GoalType(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("jobInterestType") var jobInterestType: String
)