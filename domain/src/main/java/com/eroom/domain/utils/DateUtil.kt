package com.eroom.domain.utils

fun toLocalDateFormat(year: Int, month: Int, day: Int): String {
    val tMonth = if (month < 10) "0$month" else "$month"
    val tDay = if (day < 10) "0$day" else "$day"
    return "$year-$tMonth-${tDay}T00:00:00"
}

fun toLocalDateFormat(year: String, month: String, day: String): String {
    return "$year-$month-${day}T00:00:00"
}