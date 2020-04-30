package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class MinimalGoalDetail(
    @JsonProperty("id") var id: Long,
    @JsonProperty("title") var title: String,
    @JsonProperty("description") var description: String,
    @JsonProperty("joinCount") var joinCount: Int,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("isDateFixed") var isDateFixed: Boolean,
    @JsonProperty("jobInterests") var jobInterests: ArrayList<GoalType>
)