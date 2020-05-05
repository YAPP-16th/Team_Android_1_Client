package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Member(
    @JsonProperty("uid") var id: String,
    @JsonProperty("nickname") var nickname: String,
    @JsonProperty("imagePath") var imagePath: String?,
    @JsonProperty("jobInterests") var jobInterests: ArrayList<JobInterest>
)