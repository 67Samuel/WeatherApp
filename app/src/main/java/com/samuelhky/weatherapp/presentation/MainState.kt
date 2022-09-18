package com.samuelhky.weatherapp.presentation

import com.google.android.gms.maps.model.LatLng
import com.samuelhky.weatherapp.domain.weather.WeatherInfo

data class MainState(
    val weatherInfo: WeatherInfo? = null,
    val latLng: LatLng? = null,
    val locationName: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    override fun toString(): String {
        return "MainState(weatherInfo=$weatherInfo, latLng=$latLng, locationName=$locationName, isLoading=$isLoading, error=$error)"
    }
}
