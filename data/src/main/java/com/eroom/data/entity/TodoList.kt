package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class TodoList(
    @JsonProperty("content") var content: String,
    @JsonProperty("priority") var priority: Long
)