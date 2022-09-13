package com.samuelhky.weatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.spec.NavHostEngine
import com.samuelhky.weatherapp.presentation.ui.theme.DeepBlue
import com.samuelhky.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.samuelhky.weatherapp.util.ui.BackPressHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private val TAG: String = "MainActivityDebug"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPermissionsLauncher(viewModel::loadWeatherInfo)
        if (!hasPermissions())
            requestForPermissions()
        setContent {
            WeatherAppTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    backgroundColor = Color.Transparent
                ) {
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

                    Box(modifier = Modifier.fillMaxSize()) {
                        if (viewModel.state.isLoading)
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = DeepBlue
                            )
                        viewModel.state.error?.let { error ->
                            LaunchedEffect(true) {
                                scaffoldState.snackbarHostState.showSnackbar(error)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hasPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun createPermissionsLauncher(callback: () -> Unit) {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { callback }
    }

    private fun requestForPermissions() {
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    override fun onResume() {
        super.onResume()
        // handle changes in network connection
        lifecycleScope.launch {
            viewModel.networkMonitor.isConnected.collect {
                when (it) {
                    true -> {
                        if (!hasPermissions())
                            requestForPermissions()
                        else
                            viewModel.loadWeatherInfo()
                    }
                    false -> {
                        viewModel.setErrorMessage("No internet connection")
                    }
                }
            }
        }
    }
}