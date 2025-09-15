package com.example.weatherapp.model

import com.example.weatherapp.ui.Details
import com.example.weatherapp.util.kelvinToFahrenheit
import kotlinx.serialization.Serializable

@Serializable
data class DisplayForecast(
    val temperature: Int,
    val feelsLike: Int,
    val weather: String,
    val weatherDescription: String,
)

fun Forecast.toDisplayForecast(): DisplayForecast =
    DisplayForecast(
        temperature = main?.temp?.kelvinToFahrenheit()?.toInt() ?: 0,
        feelsLike = main?.feelsLike?.kelvinToFahrenheit()?.toInt() ?: 0,
        weather = weather[0].main ?: "Unknown",
        weatherDescription = weather[0].description ?: "Unknown"
    )

fun Details.toDisplayForecast() =
    DisplayForecast(
        temperature = temperature,
        feelsLike = feelsLike,
        weather = weather,
        weatherDescription = weatherDescription
    )

fun DisplayForecast.toDetails(city: String) =
    Details(
        city = city,
        temperature = temperature,
        feelsLike = feelsLike,
        weather = weather,
        weatherDescription = weatherDescription
    )