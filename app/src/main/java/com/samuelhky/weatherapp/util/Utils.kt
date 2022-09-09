package com.samuelhky.weatherapp.util

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.samuelhky.weatherapp.presentation.destinations.WeatherScreenDestination
import java.time.LocalTime

private val TAG: String = "UtilsDebug"
fun getCurrentHour(): Int {
    val now = LocalTime.now()
    return if (now.minute < 30) now.hour else if (now.hour == 23) 0 else now.hour + 1
}

fun getLocationName(lat: Double, long: Double, context: Context): String {
    var name = "lat: ${String.format("%.3f", lat)}, long: ${String.format("%.3f", long)}"
    if (Geocoder.isPresent()) {
        val locationList =
            Geocoder(context).getFromLocation(lat, long, 1)
        if (locationList.isNotEmpty()) {
            locationList[0].apply {
                if (thoroughfare != null && countryName != null)
                    name = "${locationList[0].thoroughfare}, ${locationList[0].countryName}"
                else if (thoroughfare != null)
                    name = locationList[0].countryName
            }
        }
    }
    return name
}