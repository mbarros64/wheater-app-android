package com.mbarros64.wheater_app_android.data.db

import androidx.lifecycle.LiveData
import com.mbarros64.wheater_app_android.data.model.Forecast
import io.realm.RealmResults
import java.util.*
import io.realm.*
import java.util.concurrent.Executor

class WeatherLocalCache(
    private val ioExecutor: Executor
) {

    fun insert(forecasts: List<Forecast>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            val realm: Realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                val list = RealmList<Forecast>()
                list.addAll(forecasts)
                it.insertOrUpdate(list)
            }
            realm.close()
            insertFinished()
        }
    }

    fun forecastsByCity(name: String): LiveData<RealmResults<Forecast>> {
        val realm = Realm.getDefaultInstance()
        val result = realm.where(Forecast::class.java)
            .equalTo("city", name.toLowerCase(Locale.US))
            .sort("date", Sort.ASCENDING)
            .findAll()
        return result.asLiveData()
    }

    fun forecastsByKey(key: String): RealmResults<Forecast> {
        val realm = Realm.getDefaultInstance()
        return realm.where(Forecast::class.java)
            .equalTo("key", key)
            .findAll()
    }

    private fun <T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)
}
