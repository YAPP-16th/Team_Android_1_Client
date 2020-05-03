package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class MinimalTodoListContent(
    @JsonProperty("uid") var uid: String,
    @JsonProperty("goalId") var goalId: Long,
    @JsonProperty("role") var role: String,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("copyCount") var copyCount: Int,
    @JsonProperty("startDt") var startDt: String,
    @JsonProperty("endDt") var endDt: String,
    @JsonProperty("todoList") var todoList: ArrayList<MinimalTodoListDetail>,
    @JsonProperty("nickName") var nickName: String
)