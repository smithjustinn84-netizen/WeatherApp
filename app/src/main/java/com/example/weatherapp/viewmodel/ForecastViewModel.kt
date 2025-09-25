package com.example.weatherapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.model.DisplayForecast
import com.example.weatherapp.model.toDisplayForecast
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn


class ForecastViewModel(
    private val repository: WeatherRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    sealed class State {
        data object Loading : State()

        data class Error(
            val message: String
        ) : State()

        data class Content(
            val forecast: List<DisplayForecast>
        ) : State()
    }

    private val cityName: String =
        savedStateHandle["city"] ?: throw IllegalArgumentException("City missing")

    val uiState =
        getForecasts()
            .catch {
                if (it is JsonConvertException) {
                    emit(State.Error("Invalid JSON"))
                }
                emit(State.Error(it.message ?: "Unknown error"))
            }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = State.Loading,
            )

    private fun getForecasts(): Flow<State> = flow {
        emit(State.Loading)
        val response = repository.fetchWeather(cityName)
        if (response.cod != "200") {
            emit(State.Error(response.message ?: "Unknown error"))
            return@flow
        }
        val forecast = response.list.map { response ->
            response.toDisplayForecast()
        }
        if (forecast.isEmpty()) {
            emit(State.Error("No forecasts found"))
        } else {
            emit(State.Content(forecast))
        }
    }
}

class ForecastViewModelFactory(
    private val repository: WeatherRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
