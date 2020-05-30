package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class AlarmContent(
    @SerializedName("createDt") var createDt: String,
    @SerializedName("updateDt") var updateDt: String,
    @SerializedName("id") var id: Long,
    @SerializedName("title") var title: String,
    @SerializedName("content") var content: String,
    @SerializedName("isChecked") var isChecked: Boolean,
    @SerializedName("messageType") var messageType: String,
    @SerializedName("goalId") var goalId: Long?
)