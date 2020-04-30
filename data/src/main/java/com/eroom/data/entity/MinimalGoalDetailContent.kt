package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class MinimalGoalDetailContent(
    @JsonProperty("goalId") var goalId: Long,
    @JsonProperty("role") var role: String,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("copyCount") var copyCount: Int,
    @JsonProperty("startDt") var startDt: String,
    @JsonProperty("endDt") var endDt: String,
    @JsonProperty("minimalGoalDetail") var minimalGoalDetail: MinimalGoalDetail
)