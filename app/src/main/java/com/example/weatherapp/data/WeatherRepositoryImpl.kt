package com.example.weatherapp.data

import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.WeatherService

interface WeatherRepository {
    suspend fun fetchWeather(city: String): WeatherResponse
}

class WeatherRepositoryImpl(
    private val service: WeatherService,
) : WeatherRepository {
    override suspend fun fetchWeather(city: String) = service.fetchWeather(city)
}