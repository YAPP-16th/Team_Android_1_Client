package com.eroom.data.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("uid") var uid: String,
    @SerializedName("nickname") var nickname: String,
    @SerializedName("imagePath") var imagePath: String?,
    @SerializedName("jobInterests") var jobInterests: ArrayList<JobClassResponse>
)