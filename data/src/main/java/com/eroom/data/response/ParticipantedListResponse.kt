package com.eroom.data.response

import com.eroom.data.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty

data class ParticipantedListResponse(
    @JsonProperty("members") var members: ArrayList<Member>,
    @JsonProperty("size") var size: Int,
    @JsonProperty("totalPages") var totalPages: Int,
    @JsonProperty("totalElement") var totalElement: Int
)