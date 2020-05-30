package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("uid") var uid: String,
    @SerializedName("role") var role: String,
    @SerializedName("nickname") var nickname: String,
    @SerializedName("imagePath") var imagePath: String?,
    @SerializedName("jobInterests") var jobInterests: ArrayList<JobInterest>
)