package com.zavamed.makeitfree

import com.android.sensyneapplication.DaggerTestAppComponent
import com.android.sensyneapplication.SensyneApplication
import com.android.sensyneapplication.TestAppComponent
import com.android.sensyneapplication.TestHospitalApiModule
import com.android.sensyneapplication.di.AppComponent
import com.android.sensyneapplication.di.module.AppModule

class TestSensyneApplication : SensyneApplication() {

    override fun initAppComponent(app: SensyneApplication): AppComponent {
        testAppComponent = DaggerTestAppComponent.builder()
            .appModule(AppModule(app))
            .testHospitalApiModule(TestHospitalApiModule(this)).build()
        return testAppComponent
    }

    companion object {
        lateinit var testAppComponent: TestAppComponent
    }
}
