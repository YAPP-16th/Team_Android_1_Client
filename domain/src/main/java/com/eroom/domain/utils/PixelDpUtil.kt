package com.eroom.domain.utils

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dp: Float): Float {
    val displayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
}