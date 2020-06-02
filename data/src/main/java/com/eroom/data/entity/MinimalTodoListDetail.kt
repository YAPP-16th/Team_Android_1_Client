package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class MinimalTodoListDetail(
    @SerializedName("createDt") var createDt: String,
    @SerializedName("updateDt") var updateDt: String,
    @SerializedName("id") var id: Long,
    @SerializedName("content") var content: String,
    @SerializedName("isEnd") var isEnd: Boolean,
    @SerializedName("priority") var priority: Int
)