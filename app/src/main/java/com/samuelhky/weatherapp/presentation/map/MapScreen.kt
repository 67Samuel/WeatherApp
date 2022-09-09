package com.samuelhky.weatherapp.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelhky.weatherapp.presentation.MainViewModel
import com.samuelhky.weatherapp.presentation.destinations.WeatherScreenDestination
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.util.getLocationName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private val TAG: String = "MapScreenDebug"
@Destination
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    lat: Double = 1.35,
    long: Double = 103.87,
    viewModel: MainViewModel,
    navigator: DestinationsNavigator
) {
    val currentLocation = LatLng(lat, long)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
    }
    var createMarker by remember { mutableStateOf<LatLng?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
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
                onMapClick = {
                    // set selectedLocation to remember
                    scope.launch {
                        createMarker = it
                        viewModel.loadWeatherInfo(
                            lat = it.latitude,
                            long = it.longitude
                        )
                        navigator.navigate(WeatherScreenDestination(
                            locationName = getLocationName(it.latitude, it.longitude, context)
                        ))
                    }
                    // show snackbar and set location for text in weathercard
                }
            ) {
                MapMarker(
                    location = currentLocation,
                    title = "Current Location",
                    iconColor = BitmapDescriptorFactory.HUE_RED
                )
                createMarker?.let {
                    MapMarker(
                        location = it,
                        title = "Selected Location",
                        iconColor = BitmapDescriptorFactory.HUE_GREEN
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    lat: Double = 1.35,
    long: Double = 103.87,
) {
    val currentLocation = LatLng(lat, long)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
    }
    val west = LatLng(1.359973, 103.707467)
    val north = LatLng(1.416948, 103.800164)
    val northEast = LatLng(1.390177, 103.868142)
    val east = LatLng(1.352765, 103.940240)
    val central = LatLng(1.318099, 103.799477)

    val createMarker by remember { mutableStateOf<LatLng?>(null) }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
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
                onMapClick = {
                    scope.launch {
                        delay(1000)
                    }
                }
            ) {
                MapMarker(
                    location = currentLocation,
                    title = "Current Location",
                    iconColor = BitmapDescriptorFactory.HUE_RED
                )
                MapMarker(
                    location = west,
                    title = "West",
                    alpha = 0.7f
                )
                MapMarker(
                    location = central,
                    title = "Central",
                    alpha = 0.7f
                )
                MapMarker(
                    location = north,
                    title = "North",
                    alpha = 0.7f
                )
                MapMarker(
                    location = northEast,
                    title = "North East",
                    alpha = 0.7f
                )
                MapMarker(
                    location = east,
                    title = "East",
                    alpha = 0.7f
                )
                createMarker?.let {
                    MapMarker(
                        location = it,
                        title = "Selected Location",
                        iconColor = BitmapDescriptorFactory.HUE_GREEN
                    )
                }
            }
        }
    }
}