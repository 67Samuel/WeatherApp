package com.samuelhky.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.samuel.vikitechnicaltest.presentation.util.network.NetworkMonitor
import com.samuelhky.weatherapp.domain.location.LocationTracker
import com.samuelhky.weatherapp.domain.repository.WeatherRepository
import com.samuelhky.weatherapp.domain.util.Resource
import com.samuelhky.weatherapp.domain.weather.WeatherData
import com.samuelhky.weatherapp.domain.weather.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG: String = "MainViewModelDebug"

// We don't use use-cases/interactors here because that would be too much complexity for a simple app like this
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    val networkMonitor: NetworkMonitor
): ViewModel() {

    var state by mutableStateOf(MainState())
        private set // only this ViewModel can edit this state

    /**
     * Loads weather info using phone's location data
     */
    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            locationTracker.getCurrentLocation().let { location ->
                if (location is Resource.Error) {
                    state = state.copy(
                        isLoading = false,
                        error = location.message
                    )
                    return@launch
                } else {
                    location.data?.let {
                        state = when (val result = repository.getWeatherData(
                            long = it.longitude,
                            lat = it.latitude)
                        ) {
                            is Resource.Success -> {
                                Log.d(TAG, "loadWeatherInfo (no params): got weatherInfo: lat: ${it.latitude}\nlong: ${it.longitude}")
                                state.copy(
                                    isLoading = false,
                                    weatherInfo = result.data,
                                    latLng = LatLng(it.latitude, it.longitude)
                                )
                            }
                            is Resource.Error -> state.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
                Log.d(TAG, "loadWeatherInfo (no params): done loading weather info")
            }
        }
    }

    /**
     * Loads weather info using given lat and long
     */
    fun loadWeatherInfo(lat: Double, long: Double) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            state = when (val result = repository.getWeatherData(
                lat = lat,
                long = long)
            ) {
                is Resource.Success -> state.copy(
                    isLoading = false,
                    weatherInfo = result.data,
                    latLng = LatLng(lat, long)
                )
                is Resource.Error -> state.copy(
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }

    /**
     * Loads weather info using given LatLng
     */
    fun loadWeatherInfo(latLng: LatLng) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            state = when (val result = repository.getWeatherData(
                lat = latLng.latitude,
                long = latLng.longitude)
            ) {
                is Resource.Success -> state.copy(
                    isLoading = false,
                    weatherInfo = result.data,
                    latLng = latLng
                )
                is Resource.Error -> state.copy(
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }

    /**
     * Update location using lat and long
     */
    fun updateLocation(lat: Double, long: Double) {
        state = state.copy(latLng = LatLng(lat, long))
    }

    /**
     * Update location using LatLng
     */
    fun updateLocation(latLng: LatLng) {
        state = state.copy(latLng = latLng)
    }

    fun setErrorMessage(message: String?) {
        state = state.copy(error = message)
    }

    fun setCurrentWeatherData(data: WeatherData) {
        val weatherInfo = state.weatherInfo
        weatherInfo?.let {
            val newWeatherInfo = weatherInfo.copy(currentWeatherData = data)
            state = state.copy(weatherInfo = newWeatherInfo)
        } ?: run {
            Log.d(TAG, "setCurrentWeatherData: something strange happened. weatherInfo should always not be null by the time this function is called")
        }
    }

    override fun toString(): String {
        return "MainViewModel(state=$state)"
    }
}