package com.samuelhky.weatherapp.presentation.map

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapMarker(
    location: LatLng,
    title: String,
    iconColor: Float = BitmapDescriptorFactory.HUE_AZURE,
    alpha: Float = 1f
) {
    Marker(
        state = MarkerState(position = location),
        title = title,
        icon = BitmapDescriptorFactory.defaultMarker(iconColor),
        alpha = alpha,
    )
}