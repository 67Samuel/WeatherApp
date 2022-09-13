package com.samuelhky.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.vikitechnicaltest.presentation.util.network.NetworkMonitor
import com.samuelhky.weatherapp.domain.location.LocationTracker
import com.samuelhky.weatherapp.domain.repository.WeatherRepository
import com.samuelhky.weatherapp.domain.util.Resource
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
                                Log.d(TAG, "loadWeatherInfo: weatherInfo: lat: ${it.latitude}\nlong: ${it.longitude}")
                                state.copy(
                                    isLoading = false,
                                    weatherInfo = result.data,
                                    lat = it.latitude,
                                    long = it.longitude
                                )
                            }
                            is Resource.Error -> state.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
                Log.d(TAG, "loadWeatherInfo: done loading weather info")
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
                    lat = lat,
                    long = long
                )
                is Resource.Error -> state.copy(
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }
    fun updateLocation(lat: Double, long: Double) {
        state = state.copy(lat = lat, long = long)
    }

    fun setErrorMessage(message: String) {
        state = state.copy(error = message)
    }

    override fun toString(): String {
        return "MainViewModel(state=$state)"
    }
}