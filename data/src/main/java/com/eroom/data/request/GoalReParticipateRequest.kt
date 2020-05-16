package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

class GoalReParticipateRequest (
    @JsonProperty("changedIsEnd") var changedIsEnd : Boolean,
    @JsonProperty("endDt") var endDt: String
)
