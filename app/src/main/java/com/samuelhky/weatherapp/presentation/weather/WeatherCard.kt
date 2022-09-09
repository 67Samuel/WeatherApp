package com.samuelhky.weatherapp.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelhky.weatherapp.R.drawable
import com.samuelhky.weatherapp.domain.weather.WeatherData
import com.samuelhky.weatherapp.presentation.destinations.MapScreenDestination
import com.samuelhky.weatherapp.presentation.ui.theme.TranslucentWhite
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    weatherData: WeatherData?,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    locationName: String,
) {
    // use remember to re-format the time only when weatherData changes instead of every time HourlyWeatherDisplay changes
    weatherData?.let { data ->
        val formattedTime = remember(data) {
            data.time.format(
                DateTimeFormatter.ofPattern("HH:mm")
            )
        }
        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp,
            modifier = modifier
                .padding(16.dp)
                .height(430.dp) // about the height that accommodates all types of icons
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.75f)
                        ) {
                            Card(
                                backgroundColor = TranslucentWhite,
                                shape = RoundedCornerShape(5.dp),
                            ) {
                                Text(
                                    text = locationName,
                                    color = Color.White,
                                    fontSize = 23.sp,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .clickable {
                                            navigator.navigate(MapScreenDestination())
                                        }
                                )
                            }
                        }
                        Text(
                            text = formattedTime,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = data.weatherType.iconRes),
                        contentDescription = data.weatherType.weatherDesc,
                        modifier = Modifier.width(200.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    data.temperatureCelsius?.let {
                        Text(
                            text = "${it}°C",
                            fontSize = 50.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = data.weatherType.weatherDesc,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        data.pressure?.let {
                            WeatherDataDisplay(
                                value = it.roundToInt(),
                                unit = "hpa",
                                icon = ImageVector.vectorResource(id = drawable.ic_pressure),
                                iconTint = Color.White,
                                textStyle = TextStyle(color = Color.White)
                            )
                        }
                        data.humidity?.let {
                            WeatherDataDisplay(
                                value = it.roundToInt(),
                                unit = "%",
                                icon = ImageVector.vectorResource(id = drawable.ic_drop),
                                iconTint = Color.White,
                                textStyle = TextStyle(color = Color.White)
                            )
                        }
                        data.windSpeed?.let {
                            WeatherDataDisplay(
                                value = it.roundToInt(),
                                unit = "km/h",
                                icon = ImageVector.vectorResource(id = drawable.ic_wind),
                                iconTint = Color.White,
                                textStyle = TextStyle(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }

}