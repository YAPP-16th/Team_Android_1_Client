package com.eroom.data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ParticipatedGoalInfoResponse (
    @JsonProperty("createDt") val createDt : String,
    @JsonProperty("updateDt") val updateDt : String,
    @JsonProperty("uid") val uid : String,
    @JsonProperty("goalId") val goalId : Long,
    @JsonProperty("role") val role : String,
    @JsonProperty("isEnd") val isEnd : Boolean,
    @JsonProperty("copyCount") val copyCount : Int,
    @JsonProperty("startDt") val startDt : String,
    @JsonProperty("endDt") val endDt : String
)

