package com.eroom.data.request

import com.google.gson.annotations.SerializedName

data class GoalInfoRequest(
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String
)