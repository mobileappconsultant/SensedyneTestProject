package com.android.sensyneapplication.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.sensyneapplication.domain.DBConstants.ROOMTABLECONSTANTS.DATABASE_NAME
import com.android.sensyneapplication.domain.model.RoomHospitalItem

@Database(entities = [RoomHospitalItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao

    companion object {

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries()
                .build()
        }
    }
}
