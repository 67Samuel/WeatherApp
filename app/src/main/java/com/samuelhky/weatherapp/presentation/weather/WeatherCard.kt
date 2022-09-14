package com.samuelhky.weatherapp.presentation.weather

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.samuelhky.weatherapp.presentation.MainState
import com.samuelhky.weatherapp.presentation.destinations.MapScreenDestination
import com.samuelhky.weatherapp.presentation.ui.theme.SelectionGreen
import com.samuelhky.weatherapp.presentation.ui.theme.TranslucentWhite
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private val TAG: String = "WeatherCardDebug"
@Composable
fun WeatherCard(
    state: MainState,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    locationName: String
) {
    Log.d(TAG, "WeatherCard: recomposing")
    var locationColorState by remember { mutableStateOf(Color.Unspecified) }
    val locationColor by animateColorAsState(
        targetValue = locationColorState,
        animationSpec = keyframes {
            durationMillis = 2000
            SelectionGreen at 0
            SelectionGreen at 1500 with FastOutSlowInEasing
            locationColorState at 2000
        }
    )
    state.weatherInfo?.currentWeatherData?.let { data ->
        // use remember to re-format the time only when weatherData changes instead of every time HourlyWeatherDisplay changes
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
                        Card(
                            backgroundColor = TranslucentWhite,
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth(0.75f)
                        ) {
                            Row(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = locationName,
                                    color = locationColor,
                                    fontSize = 23.sp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 5.dp)
                                        .clickable {
                                            navigator.navigate(MapScreenDestination())
                                        }
                                )
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = drawable.ic_location_searching),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterVertically)
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
        locationColorState = Color.White // animate location text
    }

}