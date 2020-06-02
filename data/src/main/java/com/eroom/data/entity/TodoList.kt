package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class TodoList(
    @SerializedName("content") var content: String,
    @SerializedName("priority") var priority: Long
)