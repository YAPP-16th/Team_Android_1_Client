package com.eroom.data.request

import com.eroom.data.entity.TodoList
import com.google.gson.annotations.SerializedName

data class NewGoalRequest(
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("isDateFixed") var isDateFixed: Boolean,
    @SerializedName("endDt") var endDt: String,
    @SerializedName("interestIdList") var interestIdList: ArrayList<Long>,
    @SerializedName("todoList") var todoList: ArrayList<TodoList>
)