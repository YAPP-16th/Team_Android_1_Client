package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

class GoalAbandonedRequest (
    @JsonProperty("changedIsEnd") var changedIsEnd : Boolean
)