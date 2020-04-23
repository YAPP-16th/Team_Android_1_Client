package com.eroom.data.response

import com.eroom.data.entity.JobClass
import com.fasterxml.jackson.annotation.JsonProperty

data class JobGroupAndClassResponse(
    @JsonProperty("id") var id: Long,
    @JsonProperty("name") var name: String,
    @JsonProperty("jobInterests") var jobInterests: ArrayList<JobClass>
)