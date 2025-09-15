package com.example.weatherapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.model.DisplayForecast
import com.example.weatherapp.ui.composables.CityHeader
import com.example.weatherapp.ui.composables.ErrorScreen
import com.example.weatherapp.ui.composables.Loading
import com.example.weatherapp.viewmodel.ForecastViewModel

@Composable
fun ForecastScreen(
    city: String,
    viewModel: ForecastViewModel,
    modifier: Modifier = Modifier,
    onNavBarBack: () -> Unit,
    onDetails: (DisplayForecast) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ForecastScreen(
        city = city,
        uiState = uiState,
        modifier = modifier,
        onNavBarBack = onNavBarBack,
        onDetails = onDetails
    )
}

@Composable
fun ForecastScreen(
    city: String,
    uiState: ForecastViewModel.State,
    modifier: Modifier = Modifier,
    onNavBarBack: () -> Unit,
    onDetails: (DisplayForecast) -> Unit = {}
) {
    when (val state = uiState) {
        is ForecastViewModel.State.Loading -> {
            Loading()
        }

        is ForecastViewModel.State.Content -> {
            ForecastScreen(
                city = city,
                modifier = modifier,
                forecasts = state.forecast,
                onNavBarBack = onNavBarBack,
                onDetails = onDetails
            )
        }

        is ForecastViewModel.State.Error -> {
            ErrorScreen(
                city = city,
                error = state.message,
                onNavBarBack = onNavBarBack,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ForecastScreen(
    city: String,
    modifier: Modifier = Modifier,
    forecasts: List<DisplayForecast>,
    onNavBarBack: () -> Unit,
    onDetails: (DisplayForecast) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CityHeader(onNavBarBack, city)
        LazyColumn {
            itemsIndexed(forecasts) { index, forecast ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            onDetails(forecast)
                        })
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(forecast.weather)
                    Text("Temp ${forecast.temperature}°")
                }
                if (index < forecasts.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastScreenPreview() {
    ForecastScreen(
        city = "Charlotte",
        forecasts = listOf(
            DisplayForecast(97, 98, "Sunny", "Sunny"),
            DisplayForecast(68, 68, "Cloudy", "Cloudy"),
            DisplayForecast(89, 91, "Clear", "clear"),
        ),
        onNavBarBack = {},
        onDetails = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingForecastScreenPreview() {
    ForecastScreen(
        city = "Charlotte",
        uiState = ForecastViewModel.State.Loading,
        onNavBarBack = {},
        onDetails = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorForecastScreenPreview() {
    ForecastScreen(
        city = "Charlotte",
        uiState = ForecastViewModel.State.Error("City not found"),
        onNavBarBack = {},
        onDetails = {}
    )
}