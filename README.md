# WeatherApp
This is a Weather App based on and extended from [philipplackner's version](https://github.com/philipplackner/WeatherApp).

## Features
- Provides hourly weather information for 7 days from [Open Meteo](https://open-meteo.com/en)
- Uses [Google Maps API](https://developers.google.com/maps) to let users pick any location in the world from which to retrieve weather information
- Uses [OneMap reverse geocoding](https://www.onemap.gov.sg/docs/#onemap-rest-apis) to retrieve more readable/familiar building names. This feature falls back to [Google Maps reverse geocoding](https://developers.google.com/maps/documentation/geocoding/requests-reverse-geocoding) especially in cases when the location specified is outside Singapore.

## Libraries, Technologies, and Design Patterns used
- Kotlin
- Jetpack Compose
- [Compose destinations](https://github.com/raamcosta/compose-destinations)
- MVI with Clean Architecture
- Hilt for dependency injection
- FusedLocationProvider
- GoogleMaps API
- OneMap API
- Retrofit2
- Leak Canary

> ### OneMap integration
> I used OneMap reverse geocoding API to get better names for buildings/roads in Singapore.
> This API requires an access token that is supposed to expire in 3 days (It worked for at least 2 additional days for me before I changed it).
> It sometimes throws an HTTP 400 error, and I suspect that it is because the latitude and longitude values provided are not handled by OneMap since it is only focused on Singapore.

## TODOs
- Add refresh ability to update the weather data in WeatherScreen
- Add additional datasources for weather data for aggregation and/or redundancy
- Save map zoom state
