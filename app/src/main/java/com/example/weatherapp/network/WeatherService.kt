package com.example.weatherapp.network

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface WeatherService {
    suspend fun fetchWeather(city: String): WeatherResponse
}

class WeatherServiceImpl(
    private val client: HttpClient
) : WeatherService {
    override suspend fun fetchWeather(city: String): WeatherResponse {
        val response =
            client.get("https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=${BuildConfig.API_KEY}")
        return response.body()
    }
}