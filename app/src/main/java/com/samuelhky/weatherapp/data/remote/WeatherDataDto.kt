package com.samuelhky.weatherapp.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.Json

data class WeatherDataDto(
    @Keep
    val time: List<String>,
    @Keep
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @Keep
    @field:Json(name = "weathercode")
    val weatherCodes: List<Int>,
    @Keep
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @Keep
    @field:Json(name = "windspeed_10m")
    val windSpeeds: List<Double>,
    @Keep
    @field:Json(name = "relativehumidity_2m")
    val humidities: List<Double>
)
