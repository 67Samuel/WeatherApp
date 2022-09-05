package com.plcoding.weatherapp.domain.location

import android.location.Location
import com.plcoding.weatherapp.domain.util.Resource

interface LocationTracker {
    suspend fun getCurrentLocation(): Resource<Location>
}