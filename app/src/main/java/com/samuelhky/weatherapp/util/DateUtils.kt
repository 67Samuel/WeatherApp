package com.samuelhky.weatherapp.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*

fun getCurrentHour(): Int {
    val now = LocalTime.now()
    return if (now.minute < 30) now.hour else if (now.hour == 23) 0 else now.hour + 1
}

fun getDayFromIndex(index: Int): String {
    val today = LocalDate.now().dayOfWeek
    return when (index/24) {
        0 -> "Today (${today.getDisplayName(TextStyle.FULL, Locale.ENGLISH)})"
        1 -> "Tomorrow (${today.plus(1).getDisplayName(TextStyle.FULL, Locale.ENGLISH)})"
        2 -> today.plus(2).getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        3 -> today.plus(3).getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        4 -> today.plus(4).getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        5 -> today.plus(5).getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        6 -> today.plus(6).getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        else -> "Unknown"
    }
}