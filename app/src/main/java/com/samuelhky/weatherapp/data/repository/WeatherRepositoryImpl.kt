package com.samuelhky.weatherapp.data.repository

import android.util.Log
import com.samuelhky.weatherapp.data.mappers.toWeatherInfo
import com.samuelhky.weatherapp.data.remote.WeatherApi
import com.samuelhky.weatherapp.domain.repository.WeatherRepository
import com.samuelhky.weatherapp.domain.util.Resource
import com.samuelhky.weatherapp.domain.weather.WeatherInfo
import java.io.IOException
import javax.inject.Inject

private val TAG: String = "WeatherRepoImplDebug"

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        Log.d(TAG, "getWeatherData: trying to get weather data from lat: $lat, long: $long")
        return try {
//            val weatherData = api.getWeatherData(
//                lat = lat,
//                long = long
//            )
//            Log.d(TAG, "getWeatherData: weatherData: $weatherData")
//            val weatherInfo = weatherData.toWeatherInfo()
//            Log.d(TAG, "getWeatherData: weatherInfo: $weatherInfo")
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: IOException) {
            Log.e(TAG, "getWeatherData IOException: ${e.message}")
            e.printStackTrace()
            Resource.Error(
                message = "Network Error: Could not load updated weather info"
            )
        } catch (e: Exception) {
            Log.e(TAG, "getWeatherData Exception: ${e.message}")
            e.printStackTrace()
            Resource.Error(
                message = e.message ?: "Unknown Error"
            )
        }
    }
}