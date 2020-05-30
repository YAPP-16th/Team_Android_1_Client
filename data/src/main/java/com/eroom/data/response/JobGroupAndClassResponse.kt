package com.eroom.data.response

import com.eroom.data.entity.JobClass
import com.google.gson.annotations.SerializedName

data class JobGroupAndClassResponse(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("jobInterests") var jobInterests: ArrayList<JobClass>
)