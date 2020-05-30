package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class Pageable(
    @SerializedName("sort") var sort: Sort,
    @SerializedName("offset") var offset: Int,
    @SerializedName("pageNumber") var pageNumber: Int,
    @SerializedName("pageSize") var pageSize: Int,
    @SerializedName("unpaged") var unpaged: Boolean,
    @SerializedName("paged") var paged: Boolean
)