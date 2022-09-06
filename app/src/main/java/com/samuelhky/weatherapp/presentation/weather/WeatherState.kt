package com.samuelhky.weatherapp.presentation.weather

import com.samuelhky.weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    override fun toString(): String {
        return "WeatherState(weatherInfo=$weatherInfo, isLoading=$isLoading, error=$error)"
    }
}
