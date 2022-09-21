package com.samuelhky.weatherapp.data.mappers

import android.util.Log
import com.samuelhky.weatherapp.data.remote.OneMapGeocodeInfo
import com.samuelhky.weatherapp.util.Constants.ONEMAP_UNKNOWN_LOCATION

private val TAG: String = "OneMapMappersDebug"
fun OneMapGeocodeInfo.toLocationName(): String? {
    Log.d(TAG, "toLocationName: called")
    Log.d(TAG, "toLocationName: OneMapGeocodeInfo: $this")
    val result = this.GeocodeInfo.firstOrNull()?.let{
        Log.d(TAG, "toLocationName: GeocodeInfo: $it")
        if (!it.BUILDINGNAME.isNullOrBlank()) it.BUILDINGNAME
        else if (!it.ROAD.isNullOrBlank()) it.ROAD
        else ONEMAP_UNKNOWN_LOCATION
    } ?: ONEMAP_UNKNOWN_LOCATION
    Log.d(TAG, "toLocationName: result: $result")
    return result
}