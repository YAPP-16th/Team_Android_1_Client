package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class JobGroup(
    @JsonProperty("id") var id: Long,
    @JsonProperty("name") var name: String
)