package com.eroom.domain.utils

fun toLocalDateFormat(year: Int, month: Int, day: Int): String {
    val tMonth = if (month < 10) "0$month" else "$month"
    val tDay = if (day < 10) "0$day" else "$day"
    return "$year-$tMonth-${tDay}T23:59:59"
}

fun toLocalDateFormat(year: String, month: String, day: String): String {
    return "$year-$month-${day}T23:59:59"
}

fun String.toRealDateFormat(): String {
    val onlyDate = this.split("T")
    val dateList = onlyDate[0].split("-")
    val year = dateList[0].substring(2, 4)
    val month = dateList[1]
    val day = dateList[2]
    return "$year.$month.$day"
}

fun String.toMonthAndDateFormat(): String {
    val onlyDate = this.split("T")
    val dateList = onlyDate[0].split("-")
    val month = dateList[1]
    val day = dateList[2]
    return "${month}월 ${day}일"
}

fun toLocalDateNonTimeFormat(year: Int, month: Int, day: Int): String {
    val tMonth = if (month < 10) "0$month" else "$month"
    val tDay = if (day < 10) "0$day" else "$day"
    return "$year-$tMonth-${tDay}"
}

fun String.toNonTimeDate(): String = this.split("T")[0]