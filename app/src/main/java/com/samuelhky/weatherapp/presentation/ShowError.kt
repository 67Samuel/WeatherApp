package com.samuelhky.weatherapp.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShowError(
    message: String,
    modifier: Modifier = Modifier,
    snackbarDuration: SnackbarDuration = SnackbarDuration.Long
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent
    ) {
        LaunchedEffect(true) {
            scaffoldState.snackbarHostState.showSnackbar(message, duration = snackbarDuration)
        }
    }
}