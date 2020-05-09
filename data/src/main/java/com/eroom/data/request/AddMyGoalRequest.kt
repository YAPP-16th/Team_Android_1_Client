package com.eroom.data.request

import com.eroom.data.entity.TodoList
import com.fasterxml.jackson.annotation.JsonProperty

data class AddMyGoalRequest(
    @JsonProperty("goalId") var goalId: Long?,
    @JsonProperty("ownerUid") var ownerUid: String?,
    @JsonProperty("endDt") var endDt: String,
    @JsonProperty("todoList") var todoList: ArrayList<TodoList>
)