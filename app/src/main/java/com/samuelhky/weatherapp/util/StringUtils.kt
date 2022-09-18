package com.samuelhky.weatherapp.util

private val TAG: String = "StringUtilsDebug"
fun String.toTitleCase(): String {
    return this.lowercase().split(" ").map { w -> w.replaceFirstChar(Char::titlecase) }.joinToString(separator=" ")
}

fun latLngToString(lat: Double, long: Double): String {
    return "lat: ${String.format("%.3f", lat)}, long: ${String.format("%.3f", long)}"
}

fun latLngToString(lat: String, long: String): String {
    val latDouble = lat.toDouble()
    val longDouble = long.toDouble()
    return "lat: ${String.format("%.3f", latDouble)}, long: ${String.format("%.3f", longDouble)}"
}