package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.model.DisplayForecast
import com.example.weatherapp.ui.composables.CityHeader


@Composable
fun DetailsScreen(
    city: String,
    modifier: Modifier = Modifier,
    forecast: DisplayForecast,
    onNavBarBack: () -> Unit
) {
    Column(modifier) {
        CityHeader(
            onNavBarBack = onNavBarBack,
            city = city
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Text(
                text = "${forecast.temperature}°",
                fontSize = 75.sp,
                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
            Text(
                text = "Feels Like: ${forecast.feelsLike}°",
                modifier = Modifier
                    .align(
                        Alignment.End
                    )
            )
            Spacer(Modifier.padding(16.dp))
            Text(
                text = forecast.weather,
                fontSize = 32.sp
            )
            Text(text = forecast.weatherDescription)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        city = "Charlotte",
        forecast = DisplayForecast(97, 98, "Sunny", "Bright and Sunny"),
        onNavBarBack = {},
    )
}