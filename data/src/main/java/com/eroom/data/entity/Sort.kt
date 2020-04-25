package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Sort(
    @JsonProperty("sorted") var sorted: Boolean,
    @JsonProperty("unsorted") var unsorted: Boolean,
    @JsonProperty("empty") var empty: Boolean
)