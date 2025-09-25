package com.example.weatherapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.weatherapp.data.AppContainer
import com.example.weatherapp.model.toDetails
import com.example.weatherapp.model.toDisplayForecast
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.ForecastViewModel
import com.example.weatherapp.viewmodel.ForecastViewModelFactory
import kotlinx.serialization.Serializable

@Serializable
object CityLookup

@Serializable
data class ForecastList(val city: String)

@Serializable
data class Details(
    val city: String,
    val temperature: Int,
    val feelsLike: Int,
    val weather: String,
    val weatherDescription: String,
)

@Composable
fun WeatherApp(appContainer: AppContainer) {
    WeatherAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavApp(appContainer = appContainer, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun NavApp(appContainer: AppContainer, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = CityLookup) {
        composable<CityLookup> {
            LookupScreen(
                modifier = modifier,
                onLookup = { city ->
                    navController.navigate(ForecastList(city))
                }
            )
        }
        composable<ForecastList> { backStackEntry ->
            val forecastList: ForecastList = backStackEntry.toRoute()
            val viewModel: ForecastViewModel = viewModel(
                factory = ForecastViewModelFactory(
                    repository = appContainer.weatherRepository,
                    savedStateHandle = backStackEntry.savedStateHandle
                )
            )
            ForecastScreen(
                modifier = modifier,
                city = forecastList.city,
                viewModel = viewModel,
                onNavBarBack = { navController.navigateUp() },
                onDetails = { forecast ->
                    navController.navigate(forecast.toDetails(forecastList.city))
                }
            )
        }
        composable<Details> { backStackEntry ->
            val details: Details = backStackEntry.toRoute()
            DetailsScreen(
                city = details.city,
                modifier = modifier,
                forecast = details.toDisplayForecast(),
                onNavBarBack = { navController.navigateUp() }
            )
        }
    }
}
