package com.android.sensyneapplication.di

import com.android.sensyneapplication.ui.MainActivity
import com.android.sensyneapplication.ui.ViewModelModule
import com.android.sensyneapplication.ui.hospitals.HospitalListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, HospitalApiModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(hospitalListFragment: HospitalListFragment)
}
