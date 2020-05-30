package com.eroom.data.response

import com.google.gson.annotations.SerializedName

data class AddGoalResponse(
    @SerializedName("createDt") var createDt: String,
    @SerializedName("updateDt") var updataDt: String,
    @SerializedName("uid") var uid: String,
    @SerializedName("goalId") var goalId: Long,
    @SerializedName("role") var role: String,
    @SerializedName("isEnd") var isEnd: Boolean,
    @SerializedName("copyCount") var copyCount: Int,
    @SerializedName("startDt") var startDt: String,
    @SerializedName("endDt") var endDt: String
)