package com.samuelhky.weatherapp.presentation.weather

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelhky.weatherapp.domain.location.LocationTracker
import com.samuelhky.weatherapp.domain.repository.WeatherRepository
import com.samuelhky.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG: String = "WeatherViewModelDebug"

// We don't use use-cases/interactors here because that would be too much complexity for a simple app like this
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set // only this ViewModel can edit this state

    fun loadWeatherInfo() {
        Log.d(TAG, "loadWeatherInfo: loading weather info")
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
                            is Resource.Success -> state.copy(
                                isLoading = false,
                                weatherInfo = result.data
                            )
                            is Resource.Error -> state.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }

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
                    weatherInfo = result.data
                )
                is Resource.Error -> state.copy(
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }

    override fun toString(): String {
        return "WeatherViewModel(state=$state)"
    }


}