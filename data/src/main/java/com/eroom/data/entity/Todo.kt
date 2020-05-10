package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Todo(
    @JsonProperty("todoId") var todoId: Long?,
    @JsonProperty("priority") var priority: Int,
    @JsonProperty("content") var content: String,
    @JsonProperty("isEnd") var isEnd: Boolean
)