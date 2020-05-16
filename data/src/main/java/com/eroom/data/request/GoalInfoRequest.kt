package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class GoalInfoRequest(
    @JsonProperty("title") var title: String,
    @JsonProperty("description") var description: String
)