package com.mbarros64.wheater_app_android.data.model

import androidx.lifecycle.LiveData
import io.realm.RealmResults

data class ForecastResult(
    val data: LiveData<RealmResults<Forecast>>,
    val networkErrors: LiveData<String>
)
