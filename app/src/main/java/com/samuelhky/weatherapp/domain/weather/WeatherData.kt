package com.samuelhky.weatherapp.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double?,
    val pressure: Double?,
    val windSpeed: Double?,
    val humidity: Double?,
    val weatherType: WeatherType
) {
    override fun toString(): String {
        return "WeatherData(time=$time, temperatureCelsius=$temperatureCelsius, pressure=$pressure, windSpeed=$windSpeed, humidity=$humidity, weatherType=$weatherType)"
    }
}
