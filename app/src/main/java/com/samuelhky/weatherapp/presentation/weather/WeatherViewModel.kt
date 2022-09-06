package com.samuelhky.weatherapp.presentation.weather

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

// We don't use use-cases/interactors here because that would be too much complexity for a simple app like this

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set // only this ViewModel can edit this state

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
                    assert(location.data != null)
                    state = when (val result = repository.getWeatherData(
                        long = location.data!!.longitude,
                        lat = location.data.latitude)
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