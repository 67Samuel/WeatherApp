package com.samuelhky.weatherapp.presentation.weather

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuelhky.weatherapp.domain.weather.WeatherData
import com.samuelhky.weatherapp.presentation.MainState
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import com.samuelhky.weatherapp.presentation.ui.theme.DeepBlue

/**
 * The section of the weather list row and its title
 */
@Composable
fun WeatherForecast(
    state: MainState,
    selectedHourIndex: Int,
    modifier: Modifier = Modifier,
    updateWeatherCard: (WeatherData) -> Unit,
) {
    var selectedIndex by remember {
        mutableStateOf(selectedHourIndex)
    }

    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Today",
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(Modifier.height(16.dp))
            LazyRow(
                content = {
                    itemsIndexed(data) { index, weatherData ->
                        HourlyWeatherListItem(
                            weatherData = weatherData,
                            backgroundColor = if (index == selectedIndex) DeepBlue else DarkBlue,
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                                .padding(horizontal = 8.dp)
                                .clickable {
                                    updateWeatherCard(weatherData)
                                    selectedIndex = index
                                }
                        )
                    }
                },
            )
        }

    }
}