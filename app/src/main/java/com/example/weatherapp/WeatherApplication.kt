package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.AppContainer
import com.example.weatherapp.data.AppContainerImpl

class WeatherApplication : Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}