package com.samuelhky.weatherapp.presentation

import com.samuelhky.weatherapp.domain.weather.WeatherInfo

data class MainState(
    val weatherInfo: WeatherInfo? = null,
    val lat: Double = 1.3521,
    val long: Double = 103.8198,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    override fun toString(): String {
        return "MainState(weatherInfo=$weatherInfo, lat=$lat, long=$long, isLoading=$isLoading, error=$error)"
    }
}
