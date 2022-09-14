package com.samuelhky.weatherapp.presentation.weather

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.samuelhky.weatherapp.util.getDayFromIndex
import kotlinx.coroutines.launch

private val TAG: String = "WeatherForecastDebug"
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
    Log.d(TAG, "WeatherForecast: recomposing")
    var selectedIndex by remember {
        mutableStateOf(selectedHourIndex)
    }
    val listState = rememberLazyListState()

    state.weatherInfo?.weatherDataPerDay?.flatMap { (_, v) -> v }?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = getDayFromIndex(listState.firstVisibleItemIndex),
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(Modifier.height(16.dp))
            LazyRow(
                state = listState,
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

        LaunchedEffect(true) {
            listState.animateScrollToItem(index = selectedIndex)
        }
    }
}