package com.mbarros64.wheater_app_android.ui.forecasts

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mbarros64.wheater_app_android.data.WeatherRepository
import com.mbarros64.wheater_app_android.data.model.Forecast
import com.mbarros64.wheater_app_android.data.model.ForecastResult
import io.realm.RealmResults

class ForecastsViewModel(
    private val repository: WeatherRepository,
    private val prefs: SharedPreferences
) : ViewModel() {

    private var cached : Boolean = false

    private val queryLiveData = MutableLiveData<String>()
    private val forecastResult: LiveData<ForecastResult> = Transformations.map(queryLiveData) {
        repository.search(it, cached)
    }

    val forecasts: LiveData<RealmResults<Forecast>> = Transformations.switchMap(forecastResult) {
        it.data
    }
    val networkErrors: LiveData<String> = Transformations.switchMap(forecastResult) {
        it.networkErrors
    }

    fun searchForecasts(queryString: String, cached: Boolean) {
        this.cached = cached
        queryLiveData.postValue(queryString)
        setCity(queryString)
    }

    fun lastQueryValue(): String? = queryLiveData.value

    fun getCity(): String = prefs.getString("city", DEFAULT_CITY) ?: DEFAULT_CITY

    private fun setCity(city: String) {
        with (prefs.edit()) {
            putString("city", city)
            apply()
        }
    }

    companion object {
        private const val DEFAULT_CITY = "Manaus"
    }
}
