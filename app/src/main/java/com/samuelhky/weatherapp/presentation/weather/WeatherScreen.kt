package com.samuelhky.weatherapp.presentation.weather

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelhky.weatherapp.presentation.MainViewModel
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.presentation.ui.theme.DeepBlue
import com.samuelhky.weatherapp.util.BackPressHandler
import com.samuelhky.weatherapp.util.getCurrentHour
import com.samuelhky.weatherapp.util.getLocationName
import com.samuelhky.weatherapp.util.ui.ScreenTransitions

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
            viewModel.setErrorMessage(null)
            val activity = (context as? Activity)
            activity?.finish()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        viewModel.state.weatherInfo?.let {
            viewModel.state.latLng?.let { latLng ->
                WeatherCard(
                    state = viewModel.state,
                    backgroundColor = DeepBlue,
                    navigator = navigator,
                    locationName = getLocationName(
                        lat = latLng.latitude,
                        long = latLng.longitude,
                        context = context
                    )
                )
                Spacer(modifier = Modifier.fillMaxSize().weight(1f))
                WeatherForecast(
                    state = viewModel.state,
                    selectedHourIndex = getCurrentHour(),
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    viewModel.setCurrentWeatherData(it)
                }
            }
        }
    }
}