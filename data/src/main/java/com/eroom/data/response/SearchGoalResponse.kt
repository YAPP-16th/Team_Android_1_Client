package com.eroom.data.response

import com.eroom.data.entity.GoalType
import com.fasterxml.jackson.annotation.JsonProperty

data class SearchGoalResponse(
    @JsonProperty("createDt") var createDt: String,
    @JsonProperty("updateDt") var updataDt: String,
    @JsonProperty("id") var id: Long,
    @JsonProperty("title") var title: String,
    @JsonProperty("description") var description: String,
    @JsonProperty("joinCount") var joinCount: Int,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("isDateFixed") var isDateFixed: Boolean,
    @JsonProperty("startDt") var startDt: String,
    @JsonProperty("endDt") var endDt: String,
    @JsonProperty("jobInterests") var jobInterests: ArrayList<GoalType>
    )