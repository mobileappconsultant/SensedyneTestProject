package com.android.sensyneapplication.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.android.sensyneapplication.domain.model.RoomHospitalItem

@Dao
interface HospitalDao {

    @RawQuery
    fun retrieveAllHospitals(query: SupportSQLiteQuery): List<RoomHospitalItem>

    @Query("SELECT * FROM RoomHospitalItem")
    fun retrieveAllHospitals(): List<RoomHospitalItem>

    @Delete
    fun deleteHospitalEntry(quote: RoomHospitalItem): Int

    @Query("DELETE FROM RoomHospitalItem")
    fun deleteAll()
    /*
       *
       * Insert a list in the database. If the item already exists, replace it.
       *
       * @param list the items to be inserted.
       */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertHospital(hospitalList: MutableList<RoomHospitalItem>)
}
