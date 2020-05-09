package com.eroom.data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AddGoalResponse(
    @JsonProperty("createDt") var createDt: String,
    @JsonProperty("updateDt") var updataDt: String,
    @JsonProperty("uid") var uid: String,
    @JsonProperty("goalId") var goalId: Long,
    @JsonProperty("role") var role: String,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("copyCount") var copyCount: Int,
    @JsonProperty("startDt") var startDt: String,
    @JsonProperty("endDt") var endDt: String
)