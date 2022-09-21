package com.samuelhky.weatherapp.data.mappers

import com.samuelhky.weatherapp.data.remote.OneMapGeocodeInfo
import com.samuelhky.weatherapp.util.Constants.ONEMAP_UNKNOWN_LOCATION

fun OneMapGeocodeInfo.toLocationName(): String? {
    return this.GeocodeInfo.firstOrNull()?.let{
        if (!it.BUILDINGNAME.isNullOrBlank()) it.BUILDINGNAME
        else if (!it.ROAD.isNullOrBlank()) it.ROAD
        else ONEMAP_UNKNOWN_LOCATION
    } ?: ONEMAP_UNKNOWN_LOCATION
}