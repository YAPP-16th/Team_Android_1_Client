package com.eroom.domain.customview.bottomsheet

import android.os.Parcelable
import com.eroom.data.localclass.BottomSheetColor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BottomSheetInfo(
    var title: String,
    var colorInfo: BottomSheetColor
): Parcelable