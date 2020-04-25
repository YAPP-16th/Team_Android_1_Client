package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Pageable(
    @JsonProperty("sort") var sort: Sort,
    @JsonProperty("offset") var offset: Int,
    @JsonProperty("pageNumber") var pageNumber: Int,
    @JsonProperty("pageSize") var pageSize: Int,
    @JsonProperty("unpaged") var unpaged: Boolean
)