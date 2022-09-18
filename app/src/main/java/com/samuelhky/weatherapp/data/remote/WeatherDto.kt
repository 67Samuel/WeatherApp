package com.samuelhky.weatherapp.data.remote

import com.samuelhky.weatherapp.BuildConfig
import com.squareup.moshi.Json

data class WeatherDto(

    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
) {
    override fun toString(): String {
        return "WeatherDto(weatherData=$weatherData)"
    }
}
