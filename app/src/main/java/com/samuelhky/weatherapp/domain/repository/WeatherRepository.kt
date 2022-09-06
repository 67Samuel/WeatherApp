package com.samuelhky.weatherapp.domain.repository

import com.samuelhky.weatherapp.domain.util.Resource
import com.samuelhky.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}