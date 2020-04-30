package com.eroom.data.response

import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.data.entity.Pageable
import com.eroom.data.entity.Sort
import com.fasterxml.jackson.annotation.JsonProperty

class ParticipatedGoalsResponse(
    @JsonProperty("content") var content: ArrayList<MinimalGoalDetailContent>,
    @JsonProperty("pageable") var pageable: Pageable,
    @JsonProperty("totalPages") var totalPages: Int,
    @JsonProperty("totalElements") var totalElements: Int,
    @JsonProperty("last") var last: Boolean,
    @JsonProperty("number") var number: Int,
    @JsonProperty("size") var size: Int,
    @JsonProperty("first") var first: Boolean,
    @JsonProperty("sort") var sort: Sort,
    @JsonProperty("numberOfElements") var numberOfElements: Int,
    @JsonProperty("empty") var empty: Boolean
)
