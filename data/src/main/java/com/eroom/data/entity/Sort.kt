package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("sorted") var sorted: Boolean,
    @SerializedName("unsorted") var unsorted: Boolean,
    @SerializedName("empty") var empty: Boolean
)