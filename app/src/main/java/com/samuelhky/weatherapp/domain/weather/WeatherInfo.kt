package com.samuelhky.weatherapp.domain.weather


data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
) {
    override fun toString(): String {
        return "WeatherInfo(weatherDataPerDay=$weatherDataPerDay, currentWeatherData=$currentWeatherData)"
    }
}
