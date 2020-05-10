package com.eroom.data.request

import com.eroom.data.entity.Todo
import com.fasterxml.jackson.annotation.JsonProperty

data class TodoRequest(
    @JsonProperty("goalId") var goalId: Long,
    @JsonProperty("todoList") var todoList: ArrayList<Todo>
)