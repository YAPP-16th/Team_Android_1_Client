package com.eroom.data.request

import com.eroom.data.entity.TodoList
import com.fasterxml.jackson.annotation.JsonProperty

data class NewGoalRequest(
    @JsonProperty("title") var title: String,
    @JsonProperty("description") var description: String,
    @JsonProperty("isDateFixed") var isDateFixed: Boolean,
    @JsonProperty("endDt") var endDt: String,
    @JsonProperty("interestIdList") var interestIdList: ArrayList<Long>,
    @JsonProperty("todoList") var todoList: ArrayList<TodoList>
)