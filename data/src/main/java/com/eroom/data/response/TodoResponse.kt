package com.eroom.data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TodoResponse(
    @JsonProperty("createDt") var createDt: String,
    @JsonProperty("updateDt") var updateDt: String,
    @JsonProperty("id") var todoId: Long,
    @JsonProperty("content") var content: String,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("priority") var priority: Int
)