package com.samuelhky.weatherapp.data.mappers

import android.content.Context
import android.util.Log
import com.samuelhky.weatherapp.data.remote.OneMapGeocodeInfo
import com.samuelhky.weatherapp.data.util.GeocodeInfoException
import com.samuelhky.weatherapp.util.Constants.ONEMAP_UNKNOWN_LOCATION
import com.samuelhky.weatherapp.util.getLocationName
import com.samuelhky.weatherapp.util.latLngToString

private val TAG: String = "OneMapMappersDebug"
fun OneMapGeocodeInfo.toLocationName(): String? {
    Log.d(TAG, "toLocationName: called")
    return this.GeocodeInfo.firstOrNull()?.let{
        if (!it.BUILDINGNAME.isNullOrBlank()) it.BUILDINGNAME
        else if (!it.ROAD.isNullOrBlank()) it.ROAD
        else ONEMAP_UNKNOWN_LOCATION
    } ?: ONEMAP_UNKNOWN_LOCATION
}