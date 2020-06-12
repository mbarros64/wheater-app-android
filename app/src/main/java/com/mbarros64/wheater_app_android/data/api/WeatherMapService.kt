package com.mbarros64.wheater_app_android.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherMapService {

    @GET("data/2.5/forecast")
    fun loadWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric"
    ): Single<ForecastResponse>
}