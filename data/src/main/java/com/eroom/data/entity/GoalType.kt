package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class GoalType(
    @JsonProperty("id") var id: Long,
    @JsonProperty("name") var name: String,
    @JsonProperty("jobInterestType") var jobInterestType: Long
)