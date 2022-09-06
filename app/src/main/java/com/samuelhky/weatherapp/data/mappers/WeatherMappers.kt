// This file contains code related to mapping Weather Dtos to relevant models in the domain layer

package com.samuelhky.weatherapp.data.mappers

import android.util.Log
import com.samuelhky.weatherapp.data.remote.WeatherDataDto
import com.samuelhky.weatherapp.data.remote.WeatherDto
import com.samuelhky.weatherapp.domain.weather.WeatherData
import com.samuelhky.weatherapp.domain.weather.WeatherInfo
import com.samuelhky.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME

private val TAG: String = "WeatherMappersDebug"


private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { i, time ->
        IndexedWeatherData(
            i,
            WeatherData(
                time = LocalDateTime.parse(time, ISO_DATE_TIME),
                temperatureCelsius = temperatures[i],
                pressure = pressures[i],
                windSpeed = windSpeeds[i],
                humidity = humidities[i],
                weatherType = WeatherType.fromWMO(weatherCodes[i])
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { indexedWeatherData ->
            indexedWeatherData.data
        }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    Log.d(TAG, "toWeatherInfo: raw weatherData retrieved: $weatherData")
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalTime.now()
    val currentWeatherData = if (now.hour == 23 && now.minute > 30) weatherDataMap[0]?.get(0) else weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}