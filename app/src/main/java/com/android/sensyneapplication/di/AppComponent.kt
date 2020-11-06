package com.android.sensyneapplication.di

import com.android.sensyneapplication.di.module.AppModule
import com.android.sensyneapplication.di.module.DatabaseModule
import com.android.sensyneapplication.di.module.HospitalApiModule
import com.android.sensyneapplication.di.module.ViewModelModule
import com.android.sensyneapplication.ui.activities.MainActivity
import com.android.sensyneapplication.ui.fragments.HospitalListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, HospitalApiModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(hospitalListFragment: HospitalListFragment)
}
