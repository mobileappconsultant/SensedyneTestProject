package com.android.sensyneapplication.di

import android.app.Application
import com.android.sensyneapplication.domain.AppDatabase
import com.android.sensyneapplication.domain.HospitalDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    fun provideHospitalDao(appDatabase: AppDatabase): HospitalDao {
        return appDatabase.hospitalDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(appContext: Application): AppDatabase {
        return AppDatabase.buildDatabase(appContext)
    }
}
