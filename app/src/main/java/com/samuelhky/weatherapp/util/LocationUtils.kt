package com.samuelhky.weatherapp.util

import android.content.Context
import android.location.Geocoder
import android.util.Log
import java.io.IOException

private val TAG: String = "LocationUtilsDebug"
fun getLocationName(lat: Double, long: Double, context: Context): String {
    var name = "lat: ${String.format("%.3f", lat)}, long: ${String.format("%.3f", long)}"
    try {
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
    } catch (e: IOException) {
        e.printStackTrace() // possibly caused by no network connection
        Log.d(TAG, "getLocationName: check network connection")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return name
}