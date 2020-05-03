package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class MinimalTodoListDetail(
    @JsonProperty("createDt") var createDt: String,
    @JsonProperty("updateDt") var updateDt: String,
    @JsonProperty("id") var id: Long,
    @JsonProperty("content") var content: String,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("priority") var priority: Int
)