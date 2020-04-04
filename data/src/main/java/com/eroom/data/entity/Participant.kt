package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Participant(
    @JsonProperty("id") var id: Int,
    @JsonProperty("name") var name: String,
    @JsonProperty("imageUrl") var imageUrl: String
)