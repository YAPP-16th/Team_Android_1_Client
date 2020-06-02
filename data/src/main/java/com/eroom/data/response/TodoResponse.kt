package com.eroom.data.response

import com.google.gson.annotations.SerializedName

data class TodoResponse(
    @SerializedName("createDt") var createDt: String,
    @SerializedName("updateDt") var updateDt: String,
    @SerializedName("id") var todoId: Long,
    @SerializedName("content") var content: String,
    @SerializedName("isEnd") var isEnd: Boolean,
    @SerializedName("priority") var priority: Int
)