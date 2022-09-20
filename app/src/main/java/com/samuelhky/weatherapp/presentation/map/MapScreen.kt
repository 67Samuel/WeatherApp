package com.samuelhky.weatherapp.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelhky.weatherapp.presentation.MainViewModel
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.util.getLocationName
import com.samuelhky.weatherapp.util.ui.ScreenTransitions
import kotlinx.coroutines.launch

/**
 * Taken from https://www.valueof.io/blog/intercept-back-press-button-in-jetpack-compose
 */
private val TAG: String = "MapScreenDebug"
@Destination(style = ScreenTransitions::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navigator: DestinationsNavigator
) {
    viewModel.state.latLng?.let { latLng ->
        val selectedLocation = LatLng(latLng.latitude, latLng.longitude)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(selectedLocation, 15f)
        }
        val scope = rememberCoroutineScope()
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select location for weather report",
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Card(
                shape = RoundedCornerShape(10.dp)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapClick = { newLatLng ->
                        scope.launch {
                            viewModel.loadWeatherInfo(newLatLng)
                            navigator.popBackStack()
                        }
                    },
                    properties = MapProperties(isMyLocationEnabled = true)
                ) {
                    Marker(
                        state = MarkerState(position = selectedLocation),
                        title = "Selected Location",
                        snippet = viewModel.state.locationName
                    )
                }
            }
        }
    }
}
