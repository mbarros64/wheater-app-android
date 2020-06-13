package com.mbarros64.wheater_app_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mbarros64.wheater_app_android.R
import com.mbarros64.wheater_app_android.ui.forecasts.ForecastsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, ForecastsFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }
}
