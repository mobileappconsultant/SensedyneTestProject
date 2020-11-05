package com.android.sensyneapplication

import com.android.sensyneapplication.di.AppComponent
import com.android.sensyneapplication.di.module.AppModule
import com.android.sensyneapplication.di.module.DatabaseModule
import com.android.sensyneapplication.di.module.ViewModelModule
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, TestHospitalApiModule::class, ViewModelModule::class])
interface TestAppComponent : AppComponent {
    fun mockWebServer(): MockWebServer
    fun inject(mainActivityTest: MainActivityTest)
}
