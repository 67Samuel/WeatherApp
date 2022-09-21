package com.samuelhky.weatherapp.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json

data class WeatherDto(

    @Keep
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
) {
    override fun toString(): String {
        return "WeatherDto(weatherData=$weatherData)"
    }
}
