package com.plcoding.weatherapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorCard(
    message: String,
    backgroundColor: Color = Color.LightGray,
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = message,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}