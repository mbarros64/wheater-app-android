package com.mbarros64.wheater_app_android.data

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mbarros64.wheater_app_android.data.api.ForecastResponse
import com.mbarros64.wheater_app_android.data.api.WeatherMapService
import com.mbarros64.wheater_app_android.data.db.WeatherLocalCache
import com.mbarros64.wheater_app_android.data.model.Forecast
import com.mbarros64.wheater_app_android.data.model.ForecastConverter.fromResponse
import com.mbarros64.wheater_app_android.data.model.ForecastResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WeatherRepository(
    private val service: WeatherMapService,
    private val cache: WeatherLocalCache
) {

    private val networkErrors = MutableLiveData<String>()
    private var isRequestInProgress = false

    fun getByKey(key: String): Forecast? {
        val data = cache.forecastsByKey(key)
        return data.first()
    }

    fun search(query: String, cached: Boolean): ForecastResult {
        if (!cached) {
            requestAndSaveData(query)
        }
        val data = cache.forecastsByCity(query)
        return ForecastResult(data, networkErrors)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchForecasts(
            service,
            query,
            { forecasts ->
                cache.insert(forecasts) {
                    networkErrors.postValue(null)
                    isRequestInProgress = false
                }
            },
            { error ->
                networkErrors.postValue(error)
                isRequestInProgress = false
            }
        )
    }

    @SuppressLint("CheckResult")
    private fun searchForecasts(
        service: WeatherMapService,
        query: String,
        onSuccess: (repos: List<Forecast>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        service.loadWeather(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response: ForecastResponse ->
                    onSuccess(fromResponse(response))
                },
                { e: Throwable ->
                    onError(e.message ?: "Unknown error")
                }
            )
    }
}
