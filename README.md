# Weather App

author: Justin Smith

# Requirements:

gradle needs a `local.properties` file with a valid api key for the weather api.

```toml
# local.properties
API_KEY="<API_HERE>"
```

Android requirements:
Min SDK: 21

# Overview:

A weather lookup app that allows user to lookup weather for a given city.

User will open the app and enter a city name. For the given city, app will load the forecast using the forecast API provided by https://openweathermap.org/.

API: https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={api key}

the user should see a list view that lists temperature and weather.

The user can then tap on a row to get more details about that particular forecast.

Detail view of the forecast would list the temperature, feels like, weather and weather description.

# Project Structure Overview:

## Entrypoint:

- WeatherApplication.kt, Creates the Dependency Injection Container that provides the AppContainer used to supply dependencies to the rest of the app
- MainActivity.kt, Configures the Screen
- ui/WeatherApp.kt, Configures Navigation and the Compose Components

## Ui:

- LookupScreen.kt, Screen where the user enters the city name to lookup the forecast for
- ForecastScreen.kt, List of forecasts for the given city
- DetailsScreen.kt, Details of the forecast for the given city

composables: Reusable components for the UI

- CityHeader.kt, header for the city
- ErrorScreen.kt, error screen for when there is an error (very basic probably would handle this via a snackbar and logging in a production app))
- LoadingScreen.kt, loading screen for when the app is loading

## Viewmodel:

- ForecastViewModel.kt, ViewModel for the ForecastScreen
- ForecastViewModelFactory, Factory for the ForecastViewModel (don't need this if we use hilt or koin)

## Model:

- DisplayForecast.kt, model for the forecast to be displayed
- WeatherResponse.kt, model for the response from the weather API

use DTOs to prevent coupling and separation of concern

## Data:

- AppContainer.kt, Dependency Injection Container for the app (For a more complex app Hilt or Koin would be used)
- WeatherRepository.kt, Repository for the weather API (interface to allow for testing and switching dependencies)

## Network:

- WeatherService.kt, Ktor service for the weather API (Retrofit would also work but Ktor is Multiplatform and created by Jetbrains)))

## Util

- kelvinToFahrenheit.kt, helper function to convert kelvin to fahrenheit


# UI

![lookup](/images/lookup.png))
![list](/images/list.png)))
![details](/images/details.png)))
