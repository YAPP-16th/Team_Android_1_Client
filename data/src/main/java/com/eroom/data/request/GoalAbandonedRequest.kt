package com.eroom.data.request

import com.google.gson.annotations.SerializedName

class GoalAbandonedRequest (
    @SerializedName("changedIsEnd") var changedIsEnd : Boolean
)