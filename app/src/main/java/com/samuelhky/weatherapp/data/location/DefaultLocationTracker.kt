package com.samuelhky.weatherapp.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.samuelhky.weatherapp.domain.location.LocationTracker
import com.samuelhky.weatherapp.domain.util.Resource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
): LocationTracker {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Resource<Location> {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasAccessFineLocationPermission) return Resource.Error(
            message = "Unable to access location. Please enable 'Fine Location Permission'"
        )

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasAccessCoarseLocationPermission) return Resource.Error(
            message = "Unable to access location. Please enable 'Coarse Location Permission'"
        )

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) return Resource.Error(
            message = "Unable to access location: Cannot connect to GPS services."
        )

        // transform callback to coroutine
        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful)
                        cont.resume(Resource.Success(data = result))
                    else
                        cont.resume(Resource.Error(message = "Unable to retrieve location information"))
                    return@suspendCancellableCoroutine // to make sure we don't resume more than once
                }

                // asynchronously wait for result
                addOnSuccessListener {
                    cont.resume(Resource.Success(data = it))
                }
                addOnFailureListener{
                    it.printStackTrace()
                    cont.resume(Resource.Error(message = "Unable to retrieve location information"))
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }

    }
}