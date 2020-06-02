package com.eroom.data.response

import com.eroom.data.entity.Member
import com.google.gson.annotations.SerializedName

data class ParticipantedListResponse(
    @SerializedName("members") var members: ArrayList<Member>,
    @SerializedName("size") var size: Int,
    @SerializedName("totalPages") var totalPages: Int,
    @SerializedName("totalElement") var totalElement: Int
)