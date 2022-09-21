package com.samuelhky.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.samuel.vikitechnicaltest.presentation.util.network.NetworkMonitor
import com.samuelhky.weatherapp.BuildConfig
import com.samuelhky.weatherapp.domain.location.LocationTracker
import com.samuelhky.weatherapp.domain.repository.OneMapRepository
import com.samuelhky.weatherapp.domain.repository.WeatherRepository
import com.samuelhky.weatherapp.domain.util.Resource
import com.samuelhky.weatherapp.domain.weather.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG: String = "MainViewModelDebug"

// We don't use use-cases/interactors here because that would be too much complexity for a simple app like this
@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val oneMapRepository: OneMapRepository,
    private val locationTracker: LocationTracker,
    val networkMonitor: NetworkMonitor,
) : ViewModel() {

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
                        state = when (val result = weatherRepository.getWeatherData(
                            long = it.longitude,
                            lat = it.latitude)
                        ) {
                            is Resource.Success -> {
                                Log.d(TAG,
                                    "loadWeatherInfo (no params): got weatherInfo: lat: ${it.latitude}\nlong: ${it.longitude}")
                                state.copy(
                                    isLoading = false,
                                    weatherInfo = result.data,
                                    latLng = LatLng(it.latitude, it.longitude)
                                )
                            }
                            is Resource.Error -> {
                                Log.d(TAG, "loadWeatherInfo: error: ${result.message}")
                                state.copy(
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                    }
                }
                Log.d(TAG, "loadWeatherInfo (no params): done loading weather info")
            }
        }.invokeOnCompletion {
            it?.printStackTrace()
            state.latLng?.let { latLng ->
                loadLocationName(latLng)
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
            state = when (val result = weatherRepository.getWeatherData(
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
//            val oldLatLng = state.latLng
            state = state.copy(
                isLoading = true,
                error = null,
                latLng = latLng
            )
            state = when (val result = weatherRepository.getWeatherData(
                lat = latLng.latitude,
                long = latLng.longitude)
            ) {
                is Resource.Success -> state.copy(
                    isLoading = false,
                    weatherInfo = result.data
                )
                is Resource.Error -> state.copy(
                    isLoading = false,
                    error = result.message,
//                    latLng = oldLatLng
                )
            }
        }.invokeOnCompletion {
            it?.printStackTrace()
            state.latLng?.let { latLng ->
                loadLocationName(latLng)
            }
        }
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
            Log.d(TAG,
                "setCurrentWeatherData: something strange happened. weatherInfo should always not be null by the time this function is called")
        }
    }

    private fun loadLocationName(latLng: LatLng, token: String? = null) {
        Log.d(TAG, "loadLocationName: called")
        viewModelScope.launch {
            state = when (val result = oneMapRepository.getLocationName(
                latLng = latLng,
                token = token ?: BuildConfig.ONEMAP_API_KEY)
            ) {
                is Resource.Success -> state.copy(
                    isLoading = false,
                    locationName = result.data
                )
                is Resource.Error -> state.copy(
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }

    override fun toString(): String {
        return "MainViewModel(state=$state)"
    }
}