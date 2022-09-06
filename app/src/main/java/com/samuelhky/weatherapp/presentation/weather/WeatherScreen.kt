package com.samuelhky.weatherapp.presentation.weather

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.samuelhky.weatherapp.presentation.ErrorCard
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.presentation.ui.theme.DeepBlue

private val TAG: String = "WeatherScreenDebug"

@RootNavGraph(start = true)
@Destination
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    Log.d(TAG, "WeatherScreen: called")
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
        ) {
            WeatherCard(
                state = viewModel.state,
                backgroundColor = DeepBlue
            )
            Log.d(TAG, "WeatherScreen: weatherCard created")
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(state = viewModel.state)
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