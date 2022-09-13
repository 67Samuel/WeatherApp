package com.samuelhky.weatherapp.util

import java.time.LocalTime

fun getCurrentHour(): Int {
    val now = LocalTime.now()
    return if (now.minute < 30) now.hour else if (now.hour == 23) 0 else now.hour + 1
}