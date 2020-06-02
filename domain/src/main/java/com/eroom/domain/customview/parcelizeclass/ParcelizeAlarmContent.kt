package com.eroom.domain.customview.parcelizeclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelizeAlarmContent(
    var id: Long,
    var title: String,
    var content: String,
    var goalId: Long?
): Parcelable