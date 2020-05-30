package com.eroom.data.response

import com.google.gson.annotations.SerializedName

data class ParticipatedGoalInfoResponse (
    @SerializedName("createDt") val createDt : String,
    @SerializedName("updateDt") val updateDt : String,
    @SerializedName("uid") val uid : String,
    @SerializedName("goalId") val goalId : Long,
    @SerializedName("role") val role : String,
    @SerializedName("isEnd") val isEnd : Boolean,
    @SerializedName("copyCount") val copyCount : Int,
    @SerializedName("startDt") val startDt : String,
    @SerializedName("endDt") val endDt : String
)

