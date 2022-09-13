package com.samuelhky.weatherapp.util

import android.content.Context
import android.location.Geocoder

fun getLocationName(lat: Double, long: Double, context: Context): String {
    var name = "lat: ${String.format("%.3f", lat)}, long: ${String.format("%.3f", long)}"
    if (Geocoder.isPresent()) {
        val locationList =
            Geocoder(context).getFromLocation(lat, long, 1)
        if (locationList.isNotEmpty()) {
            locationList[0].apply {
                if (thoroughfare != null && countryCode != null)
                    name = "${locationList[0].thoroughfare}, ${locationList[0].countryCode}"
                else if (thoroughfare != null)
                    name = locationList[0].countryCode
            }
        }
    }
    return name
}