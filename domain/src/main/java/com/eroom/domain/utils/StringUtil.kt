package com.eroom.domain.utils

import android.os.Looper
import java.lang.StringBuilder

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