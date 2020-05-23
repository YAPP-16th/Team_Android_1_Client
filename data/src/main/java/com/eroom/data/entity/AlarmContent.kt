package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class AlarmContent(
    @JsonProperty("createDt") var createDt: String,
    @JsonProperty("updateDt") var updateDt: String,
    @JsonProperty("id") var id: Long,
    @JsonProperty("title") var title: String,
    @JsonProperty("content") var content: String,
    @JsonProperty("isChecked") var isChecked: Boolean,
    @JsonProperty("messageType") var messageType: String,
    @JsonProperty("goalId") var goalId: Long?
)