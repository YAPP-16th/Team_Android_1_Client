package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class IdListRequest(
    @JsonProperty("ids") var ids: ArrayList<Long>
)