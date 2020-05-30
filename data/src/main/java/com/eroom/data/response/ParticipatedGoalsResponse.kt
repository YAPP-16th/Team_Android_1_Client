package com.eroom.data.response

import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.data.entity.Pageable
import com.eroom.data.entity.Sort
import com.google.gson.annotations.SerializedName

class ParticipatedGoalsResponse(
    @SerializedName("content") var content: ArrayList<MinimalGoalDetailContent>,
    @SerializedName("pageable") var pageable: Pageable,
    @SerializedName("totalPages") var totalPages: Int,
    @SerializedName("totalElements") var totalElements: Int,
    @SerializedName("last") var last: Boolean,
    @SerializedName("number") var number: Int,
    @SerializedName("size") var size: Int,
    @SerializedName("first") var first: Boolean,
    @SerializedName("sort") var sort: Sort,
    @SerializedName("numberOfElements") var numberOfElements: Int,
    @SerializedName("empty") var empty: Boolean
)
