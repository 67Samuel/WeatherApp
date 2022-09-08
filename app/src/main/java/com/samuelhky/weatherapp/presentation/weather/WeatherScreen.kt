package com.samuelhky.weatherapp.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelhky.weatherapp.presentation.ErrorCard
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.presentation.ui.theme.DeepBlue
import com.samuelhky.weatherapp.util.getCurrentHour

private val TAG: String = "WeatherScreenDebug"

@RootNavGraph(start = true)
@Destination
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    navigator: DestinationsNavigator,
    locationName: String?
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
        ) {
            var mainWeatherData by remember {
                mutableStateOf(viewModel.state.weatherInfo?.currentWeatherData)
            }
            WeatherCard(
                // mainWeatherData tends to be null at first but not viewModel.state.weatherInfo?.currentWeatherData for some reason
                weatherData = mainWeatherData ?: viewModel.state.weatherInfo?.currentWeatherData,
                backgroundColor = DeepBlue,
                navigator = navigator,
                locationName = locationName
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(
                state = viewModel.state,
                selectedHourIndex = getCurrentHour()
            ) {
                mainWeatherData = it
            }
        }

        if (viewModel.state.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = DeepBlue
            )

        viewModel.state.error?.let { error ->
            ErrorCard(
                message = error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}