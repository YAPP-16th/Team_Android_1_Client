package com.eroom.data.request

import com.google.gson.annotations.SerializedName

class GoalReParticipateRequest (
    @SerializedName("changedIsEnd") var changedIsEnd : Boolean,
    @SerializedName("endDt") var endDt: String
)
