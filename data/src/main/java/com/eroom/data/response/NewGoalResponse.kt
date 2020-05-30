package com.eroom.data.response

import com.google.gson.annotations.SerializedName

data class NewGoalResponse(
    @SerializedName("createDt") var createDt: String,
    @SerializedName("updateDt") var updataDt: String,
    @SerializedName("id") var id: Long,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("joinCount") var joinCount: Int,
    @SerializedName("isEnd") var isEnd: Boolean,
    @SerializedName("isDateFixed") var isDateFixed: Boolean,
    @SerializedName("startDt") var startDt: String,
    @SerializedName("endDt") var endDt: String
)