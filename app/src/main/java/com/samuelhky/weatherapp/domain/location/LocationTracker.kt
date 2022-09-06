package com.samuelhky.weatherapp.domain.location

import android.location.Location
import com.samuelhky.weatherapp.domain.util.Resource

interface LocationTracker {
    suspend fun getCurrentLocation(): Resource<Location>
}