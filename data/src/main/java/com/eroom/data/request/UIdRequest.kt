package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UIdRequest(
    @JsonProperty("uid") var uid: String
)