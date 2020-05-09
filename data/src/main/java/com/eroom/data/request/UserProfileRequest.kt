package com.eroom.data.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.File

data class UserProfileRequest(
    @JsonProperty("multipartImageFile") var multipartImageFile: File
)