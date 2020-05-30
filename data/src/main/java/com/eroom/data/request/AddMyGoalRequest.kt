package com.eroom.data.request

import com.eroom.data.entity.TodoList
import com.google.gson.annotations.SerializedName

data class AddMyGoalRequest(
    @SerializedName("goalId") var goalId: Long?,
    @SerializedName("ownerUid") var ownerUid: String?,
    @SerializedName("endDt") var endDt: String,
    @SerializedName("todoList") var todoList: ArrayList<TodoList>
)