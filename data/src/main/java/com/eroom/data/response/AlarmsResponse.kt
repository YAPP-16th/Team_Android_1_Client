package com.eroom.data.response

import com.eroom.data.entity.AlarmContent
import com.eroom.data.entity.Pageable
import com.eroom.data.entity.Sort
import com.google.gson.annotations.SerializedName

data class AlarmsResponse(
    @SerializedName("content") var content: ArrayList<AlarmContent>,
    @SerializedName("pageable") var pageable: Pageable,
    @SerializedName("totalElement") var totalElement: Int,
    @SerializedName("last") var last: Boolean,
    @SerializedName("totalPages") var totalPages: Int,
    @SerializedName("size") var size: Int,
    @SerializedName("number") var number: Int,
    @SerializedName("sort") var sort: Sort,
    @SerializedName("numberOfElements") var numberOfElement: Int,
    @SerializedName("first") var first: Boolean,
    @SerializedName("empty") var empty: Boolean
)