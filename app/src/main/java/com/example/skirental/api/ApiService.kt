package com.example.skirental.api

import com.bersyte.weatherapp.model.Weather
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("weather/Luanda")
    suspend fun getWeather(): Response<Weather>

}