package com.android.sensyneapplication.di.module

import android.app.Application
import com.android.sensyneapplication.domain.database.AppDatabase
import com.android.sensyneapplication.domain.database.HospitalDao
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
