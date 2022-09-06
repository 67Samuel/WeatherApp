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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
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
        requestPermissions()
        setContent {
            WeatherAppTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    // To tie WeatherViewModel to the activity, making it available to all destinations
                    dependenciesContainerBuilder = {
                        dependency(hiltViewModel<WeatherViewModel>(this@MainActivity))
                    }
                )
            }
        }
    }

    private fun requestPermissions() {
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
    }
}