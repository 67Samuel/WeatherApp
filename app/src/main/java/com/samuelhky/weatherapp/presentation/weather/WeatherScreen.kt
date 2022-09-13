package com.samuelhky.weatherapp.presentation.weather

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelhky.weatherapp.presentation.MainViewModel
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.presentation.ui.theme.DeepBlue
import com.samuelhky.weatherapp.util.ui.ScreenTransitions
import com.samuelhky.weatherapp.util.getCurrentHour
import com.samuelhky.weatherapp.util.getLocationName
import com.samuelhky.weatherapp.util.BackPressHandler

private val TAG: String = "WeatherScreenDebug"

@RootNavGraph(start = true)
@Destination(style = ScreenTransitions::class)
@Composable
fun WeatherScreen(
    viewModel: MainViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    BackPressHandler {
        if (viewModel.state.error != "Press back again to exit app")
            viewModel.setErrorMessage("Press back again to exit app")
        else {
            val activity = (context as? Activity)
            activity?.finish()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        viewModel.state.weatherInfo?.let { weatherInfo ->
            var mainWeatherData by remember {
                mutableStateOf(weatherInfo.currentWeatherData)
            }
            WeatherCard(
                weatherData = mainWeatherData,
                backgroundColor = DeepBlue,
                navigator = navigator,
                locationName = getLocationName(
                    lat = viewModel.state.lat,
                    long = viewModel.state.long,
                    context = context
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(
                state = viewModel.state,
                selectedHourIndex = getCurrentHour()
            ) {
                mainWeatherData = it
            }
        }
    }
}