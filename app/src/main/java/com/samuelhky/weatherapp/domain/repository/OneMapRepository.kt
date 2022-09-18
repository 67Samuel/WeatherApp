package com.samuelhky.weatherapp.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.samuelhky.weatherapp.data.remote.OneMapGeocodeInfo
import com.samuelhky.weatherapp.domain.util.Resource

interface OneMapRepository {
    suspend fun getLocationName(latLng: LatLng, token: String): Resource<String>
}