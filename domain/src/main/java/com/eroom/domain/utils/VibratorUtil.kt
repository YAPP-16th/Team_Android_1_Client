package com.eroom.domain.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun Context.vibrateShort() {
    val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(100, 100))
    } else {
        vibrator.vibrate(100)
    }
    Thread.sleep(300)
}