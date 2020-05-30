package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("todoId") var todoId: Long?,
    @SerializedName("priority") var priority: Int,
    @SerializedName("content") var content: String,
    @SerializedName("isEnd") var isEnd: Boolean
)