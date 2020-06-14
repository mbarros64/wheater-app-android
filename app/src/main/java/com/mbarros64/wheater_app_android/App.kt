package com.mbarros64.wheater_app_android

import android.app.Application
import com.mbarros64.wheater_app_android.data.WeatherWorker
import com.mbarros64.wheater_app_android.di.appModule
import com.mbarros64.wheater_app_android.di.databaseModule
import com.mbarros64.wheater_app_android.di.networkModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, networkModule, databaseModule))
        }
        initRealm(this)
    }

    private fun initRealm(app: App) {
        Realm.init(app)
        val realmConfig = RealmConfiguration.Builder()
            .name(DB_NAME)
            .schemaVersion(DB_VERSION)
            .build()
        Realm.setDefaultConfiguration(realmConfig)
        WeatherWorker.init(applicationContext, DB_CLEAR_INTERVAL_HOURS)
    }

    companion object {
        private const val DB_NAME = "openweatherforecast.realm"
        private const val DB_VERSION = 0L
        private const val DB_CLEAR_INTERVAL_HOURS = 12L
    }
}
