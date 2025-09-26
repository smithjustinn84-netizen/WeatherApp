package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface WeatherService {
    suspend fun fetchWeather(city: String): WeatherResponse
}

class WeatherServiceImpl(
    private val client: HttpClient
) : WeatherService {
    override suspend fun fetchWeather(city: String): WeatherResponse {
        val response =
            client.get("data/2.5/forecast") {
                parameter("q", city)
            }
        return response.body()
    }
}