package com.samuelhky.weatherapp.data.remote

import androidx.annotation.Keep

data class OneMapGeocodeInfo(
    @Keep
    val GeocodeInfo: List<GeocodeInfo>
)