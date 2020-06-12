package com.mbarros64.wheater_app_android.data.db

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults


class RealmLiveData<T : RealmModel>(private var results: RealmResults<T>) :
    LiveData<RealmResults<T>>() {

    private val listener = RealmChangeListener<RealmResults<T>> { results -> value = results }

    override fun onActive() {
        results.addChangeListener(listener)
        listener.onChange(results)
    }

    override fun onInactive() {
        results.removeChangeListener(listener)
    }
}
