package com.samuelhky.weatherapp.data.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.samuelhky.weatherapp.data.mappers.toLocationName
import com.samuelhky.weatherapp.data.remote.OneMapApi
import com.samuelhky.weatherapp.data.util.GeocodeInfoException
import com.samuelhky.weatherapp.domain.repository.OneMapRepository
import com.samuelhky.weatherapp.domain.util.Resource
import com.samuelhky.weatherapp.util.Constants
import com.samuelhky.weatherapp.util.latLngToString
import retrofit2.HttpException
import javax.inject.Inject

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
        } catch (e: HttpException) {
            Log.e(TAG, "getLocationName: HTTP exception, likely because coords are outside SG domain. Defaulting to Google Geocoder", )
            e.printStackTrace()
            Resource.Success(
                data = Constants.ONEMAP_UNKNOWN_LOCATION
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                message = e.message ?: "Unknown error when calling OneMap Api"
            )
        }
    }
}