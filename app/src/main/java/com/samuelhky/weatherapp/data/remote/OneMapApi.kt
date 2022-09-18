package com.samuelhky.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OneMapApi {

    /**
     * @param location Comma separated String eg. "{lat}, {long}"
     */
    @GET("/privateapi/commonsvc/revgeocode")
    suspend fun getLocationName(
        @Query("location") location: String,
        @Query("token") token: String
    ): OneMapGeocodeInfo
}