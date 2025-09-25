package com.example.weatherapp.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class WeatherResponse(
    val cod: String? = null,
    val message: String? = null,
    val cnt: Int? = null,
    val list: List<Forecast> = emptyList(),
    val city: City? = null
)

@Serializable
data class City(
    val id: Int? = null,
    val name: String? = null,
    val coord: Coord? = null,
    val country: String? = null,
    val population: Int? = null,
    val timezone: Int? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)

@Serializable
data class Clouds(
    val all: Int? = null
)

@Serializable
data class Coord(
    val lat: Double? = null,
    val lon: Double? = null
)

@Serializable
data class Forecast(
    val dt: Long? = null,
    val main: Main? = null,
    val weather: List<Weather> = emptyList(),
    val clouds: Clouds? = null,
    val wind: Wind? = null,
    val visibility: Int? = null,
    val pop: Double? = null,
    val sys: Sys? = null,
    @SerialName("dt_txt") val dtTxt: String? = null
)

@Serializable
data class Main(
    val temp: Double? = null,
    @SerialName("feels_like")
    val feelsLike: Double? = null,
    @SerialName("temp_min")
    val tempMin: Double? = null,
    @SerialName("temp_max")
    val tempMax: Double? = null,
    val pressure: Int? = null,
    @SerialName("sea_level")
    val seaLevel: Int? = null,
    @SerialName("grnd_level")
    val grndLevel: Int? = null,
    val humidity: Int? = null,
    @SerialName("temp_kf")
    val tempKf: Double? = null
)

@Serializable
data class Sys(
    val pod: String? = null
)

@Serializable
data class Wind(
    val speed: Double? = null,
    val deg: Int? = null,
    val gust: Double? = null
)

@Serializable
data class Weather(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)
