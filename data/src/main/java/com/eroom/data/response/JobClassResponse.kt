package com.eroom.data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class JobClassResponse(
    @JsonProperty("id") var id: Long,
    @JsonProperty("name") var name: String,
    @JsonProperty("jobGroupId") var jobGroupId: Long
)