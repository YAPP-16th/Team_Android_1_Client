package com.eroom.data.request

import com.eroom.data.entity.Todo
import com.google.gson.annotations.SerializedName

data class TodoRequest(
    @SerializedName("goalId") var goalId: Long,
    @SerializedName("todoList") var todoList: ArrayList<Todo>
)