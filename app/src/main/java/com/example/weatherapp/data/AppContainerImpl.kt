package com.example.weatherapp.data

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.network.WeatherService
import com.example.weatherapp.network.WeatherServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val weatherRepository: WeatherRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 * In production I would use Hilt/Dagger or Koin for dependency injection.
 */
class AppContainerImpl : AppContainer {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val weatherService: WeatherService by lazy {
        WeatherServiceImpl(
            client = HttpClient {
                install(ContentNegotiation) {
                    json(json, contentType = ContentType.Any)
                }
                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = "api.openweathermap.org"
                        parameters.append("appid", BuildConfig.API_KEY)
                    }
                }
            }
        )
    }

    override val weatherRepository: WeatherRepositoryImpl by lazy {
        WeatherRepositoryImpl(weatherService)
    }

}