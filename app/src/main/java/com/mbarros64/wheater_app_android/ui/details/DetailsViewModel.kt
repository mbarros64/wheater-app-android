package com.mbarros64.wheater_app_android.ui.details

import androidx.lifecycle.ViewModel
import com.mbarros64.wheater_app_android.data.WeatherRepository
import com.mbarros64.wheater_app_android.data.model.Forecast

class DetailsViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    fun getForecast(key: String): Forecast? {
        return repository.getByKey(key)
    }
}
