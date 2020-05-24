package com.eroom.data.response

import com.eroom.data.entity.AlarmContent
import com.eroom.data.entity.Pageable
import com.eroom.data.entity.Sort
import com.fasterxml.jackson.annotation.JsonProperty

data class AlarmsResponse(
    @JsonProperty("content") var content: ArrayList<AlarmContent>,
    @JsonProperty("pageable") var pageable: Pageable,
    @JsonProperty("totalElement") var totalElement: Int,
    @JsonProperty("last") var last: Boolean,
    @JsonProperty("totalPages") var totalPages: Int,
    @JsonProperty("size") var size: Int,
    @JsonProperty("number") var number: Int,
    @JsonProperty("sort") var sort: Sort,
    @JsonProperty("numberOfElements") var numberOfElement: Int,
    @JsonProperty("first") var first: Boolean,
    @JsonProperty("empty") var empty: Boolean
)