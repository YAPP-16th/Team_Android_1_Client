package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class GoalContent(
    @JsonProperty("createDt") var createDt: String?,
    @JsonProperty("updateDt") var updateDt: String?,
    @JsonProperty("id") var id: Long,
    @JsonProperty("title") var title: String,
    @JsonProperty("description") var description: String,
    @JsonProperty("joinCount") var joinCount: Int,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("isDateFixed") var isDateFixed: Boolean,
    @JsonProperty("startDt") var startDt: String,
    @JsonProperty("endDt") var endDt: String,
    @JsonProperty("jobInterests") var jobInterests: ArrayList<GoalType>,
    @JsonProperty("userImages") var userImages: ArrayList<String>
)