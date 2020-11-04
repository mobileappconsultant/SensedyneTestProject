package com.android.sensyneapplication

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.sensyneapplication.domain.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class DatabaseTest {
    protected lateinit var appDatabase: AppDatabase

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }
}
