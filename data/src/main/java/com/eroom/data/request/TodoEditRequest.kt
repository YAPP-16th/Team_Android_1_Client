package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class TodoEditRequest(
    @JsonProperty("changedIsEnd") var changedIsEnd: String
)