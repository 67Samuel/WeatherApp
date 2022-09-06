package com.samuelhky.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.DestinationsNavHost
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.presentation.ui.theme.DeepBlue
import com.samuelhky.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.samuelhky.weatherapp.presentation.weather.*
import dagger.hilt.android.AndroidEntryPoint

private val TAG: String = "MainActivityDebug"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // create a contract for requesting permissions
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            // at this point, the user has been asked to give permissions, but whatever they choose, we can just call
            // loadWeatherInfo(), because we handle all the cases for whether permissions were granted or not in there
            viewModel.loadWeatherInfo()
        }
        // launch the permission (and call loadWeatherInfo())
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
        setContent {
            WeatherAppTheme {
//                DestinationsNavHost(navGraph = NavGraphs.root)
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
        }
    }
}