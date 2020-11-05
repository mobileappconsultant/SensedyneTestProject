package com.android.sensyneapplication

import android.app.Application
import com.android.sensyneapplication.di.AppComponent
import com.android.sensyneapplication.di.module.AppModule
import com.android.sensyneapplication.di.DaggerAppComponent
import com.android.sensyneapplication.di.module.HospitalApiModule
import timber.log.Timber

open class SensyneApplication : Application() {

    lateinit var appComponent: AppComponent

    open fun initAppComponent(app: SensyneApplication): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(app)).hospitalApiModule(HospitalApiModule())
            .build()
    }

    companion object {
        @get:Synchronized
        lateinit var application: SensyneApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = initAppComponent(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
