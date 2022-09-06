package com.samuelhky.weatherapp.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samuelhky.weatherapp.domain.weather.WeatherData
import com.samuelhky.weatherapp.presentation.ui.theme.DarkBlue
import java.time.format.DateTimeFormatter

/**
 * The list item for the hourly weather list
 */
@Composable
fun HourlyWeatherListItem(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    backgroundColor: Color = DarkBlue
) {
    // use remember to re-format the time only when weatherData changes instead of every time HourlyWeatherDisplay changes
    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }

    Card(
        modifier = modifier,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedTime,
                color = Color.LightGray
            )
            Image(
                painter = painterResource(id = weatherData.weatherType.iconRes),
                contentDescription = weatherData.weatherType.weatherDesc,
                modifier = Modifier.width(40.dp)
            )
            weatherData.temperatureCelsius?.let {
                Text(
                    text = "${it}°C",
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}