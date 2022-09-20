package com.samuelhky.weatherapp.data.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.currentCompositionLocalContext
import com.google.android.gms.maps.model.LatLng
import com.samuelhky.weatherapp.data.mappers.toLocationName
import com.samuelhky.weatherapp.data.remote.OneMapApi
import com.samuelhky.weatherapp.data.util.GeocodeInfoException
import com.samuelhky.weatherapp.domain.repository.OneMapRepository
import com.samuelhky.weatherapp.domain.util.Resource
import com.samuelhky.weatherapp.util.latLngToString
import kotlinx.coroutines.currentCoroutineContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

private val TAG: String = "OneMapRepoDebug"
class OneMapRepositoryImpl @Inject constructor(
    val api: OneMapApi
): OneMapRepository {
    override suspend fun getLocationName(latLng: LatLng, token: String): Resource<String> {
        Log.d(TAG, "getLocationName: called")
        return try {
            Resource.Success(
                data = api.getLocationName(
                    location = "${latLng.latitude},${latLng.longitude}", // must conform to url format
                    token = token
                ).toLocationName()
            )
        } catch (e: GeocodeInfoException) {
            e.printStackTrace()
            // no nearby buildings/roads => display lat/lng
            Resource.Success(
                data = latLngToString(latLng.latitude, latLng.longitude)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                message = e.message ?: "Unknown error when calling OneMap Api"
            )
        }
    }
}