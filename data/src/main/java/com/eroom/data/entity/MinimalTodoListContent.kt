package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class MinimalTodoListContent(
    @SerializedName("uid") var uid: String,
    @SerializedName("goalId") var goalId: Long,
    @SerializedName("role") var role: String,
    @SerializedName("isEnd") var isEnd: Boolean,
    @SerializedName("copyCount") var copyCount: Int,
    @SerializedName("startDt") var startDt: String,
    @SerializedName("endDt") var endDt: String,
    @SerializedName("todoList") var todoList: ArrayList<MinimalTodoListDetail>,
    @SerializedName("nickName") var nickName: String
)