package com.samuelhky.weatherapp.data.remote

data class GeocodeInfo(
    val BLOCK: String?,
    val BUILDINGNAME: String?,
    val FEATURE_NAME: String?,
    val LATITUDE: String,
    val LONGITUDE: String,
    val POSTALCODE: String?,
    val ROAD: String?,
    val XCOORD: String,
    val YCOORD: String
)