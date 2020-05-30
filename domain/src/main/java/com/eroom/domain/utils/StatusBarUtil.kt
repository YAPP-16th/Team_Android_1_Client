package com.eroom.domain.utils

import androidx.core.content.ContextCompat
import android.app.Activity


fun statusBarColor(activity: Activity, colorType: Int) {
    activity.window.statusBarColor =
        ContextCompat.getColor(activity, colorType)
}