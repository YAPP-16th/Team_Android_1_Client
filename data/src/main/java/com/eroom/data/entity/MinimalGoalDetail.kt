package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class MinimalGoalDetail(
    @SerializedName("id") var id: Long,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("joinCount") var joinCount: Int,
    @SerializedName("isEnd") var isEnd: Boolean,
    @SerializedName("isDateFixed") var isDateFixed: Boolean,
    @SerializedName("jobInterests") var jobInterests: ArrayList<GoalType>
)