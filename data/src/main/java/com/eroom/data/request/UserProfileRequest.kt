package com.eroom.data.request

import com.google.gson.annotations.SerializedName
import java.io.File

data class UserProfileRequest(
    @SerializedName("multipartImageFile") var multipartImageFile: File
)