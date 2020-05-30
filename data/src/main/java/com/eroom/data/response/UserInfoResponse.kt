package com.eroom.data.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoResponse(
    @JsonProperty("uid") var uid: String,
    @JsonProperty("nickname") var nickname: String,
    @JsonProperty("imagePath") var imagePath: String?,
    @JsonProperty("jobInterests") var jobInterests: ArrayList<JobClassResponse>
)