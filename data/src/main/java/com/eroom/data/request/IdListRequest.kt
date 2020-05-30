package com.eroom.data.request

import com.google.gson.annotations.SerializedName

data class IdListRequest(
    @SerializedName("ids") var ids: ArrayList<Long>
)