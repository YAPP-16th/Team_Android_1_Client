package com.eroom.domain.utils

import android.os.Build
import android.os.Looper
import android.text.Html
import android.text.Spanned


infix fun String.add(string: String): String =
    if (Looper.myLooper() == Looper.getMainLooper())
        StringBuilder().apply {
            append(this@add, string)
        }.toString()
    else StringBuffer().apply {
        append(this@add, string)
    }.toString()

fun List<String>.join(): String =
    if (Looper.myLooper() == Looper.getMainLooper())
        StringBuilder().apply {
            for (item in this@join) {
                append(item)
            }
        }.toString()
    else StringBuffer().apply {
        for (item in this@join) {
            append(item)
        }
    }.toString()

fun fromHtml(source: String?): Spanned? {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        // noinspection deprecation
        Html.fromHtml(source)
    } else Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
}