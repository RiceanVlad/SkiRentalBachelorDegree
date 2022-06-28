package com.example.skirental.api

import com.example.skirental.models.Weather
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("weather/Brasov")
    suspend fun getWeather(): Response<Weather>

}