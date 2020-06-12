package com.mbarros64.wheater_app_android.di

import com.mbarros64.wheater_app_android.data.db.WeatherLocalCache
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val databaseModule = module {

    factory { Executors.newSingleThreadExecutor() as Executor }
    factory { WeatherLocalCache(get()) as WeatherLocalCache }
}
