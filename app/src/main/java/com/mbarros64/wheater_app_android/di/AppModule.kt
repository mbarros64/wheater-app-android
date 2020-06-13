package com.mbarros64.wheater_app_android.di

import android.content.Context
import android.content.SharedPreferences
import com.mbarros64.wheater_app_android.data.WeatherRepository
import com.mbarros64.wheater_app_android.ui.details.DetailsViewModel
import com.mbarros64.wheater_app_android.ui.forecasts.ForecastsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val PREF_FILE_NAME = "weather_prefs"

val appModule = module {

    single { (get() as Context).getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE) as SharedPreferences }
    factory { WeatherRepository(get(), get()) }
    viewModel { ForecastsViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }
}
