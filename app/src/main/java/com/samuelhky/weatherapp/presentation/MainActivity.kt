package com.samuelhky.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.NavHostEngine
import com.samuelhky.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG: String = "MainActivityDebug"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContent {
            WeatherAppTheme {
                val navController: NavHostController = rememberAnimatedNavController()
                val navHostEngine: NavHostEngine = rememberAnimatedNavHostEngine()
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    // To tie MainViewModel to the activity, making it available to all destinations
                    dependenciesContainerBuilder = {
                        dependency(hiltViewModel<MainViewModel>(this@MainActivity))
                    },
                    engine = navHostEngine,
                    navController = navController
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