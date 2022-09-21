package com.samuelhky.weatherapp.data.remote

import androidx.annotation.Keep

@Keep
data class GeocodeInfo(
    val BUILDINGNAME: String?,
    val ROAD: String?
)