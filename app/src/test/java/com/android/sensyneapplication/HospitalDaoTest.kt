package com.android.sensyneapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class HospitalDaoTest : DatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertSingleHospitalTest() {
        val predefinedEntrySize = 1
        appDatabase.hospitalDao()
            .insertHospital(mutableListOf(HospitalDataFactory.makeSingleRoomHospitalEntry()))
        val hospitalEntrySize = appDatabase.hospitalDao().retrieveAllHospitals().size
        Assert.assertEquals(hospitalEntrySize, predefinedEntrySize)
    }

    @Test
    fun insertMultipleHospitalsTest() {
        val predefinedEntrySize = 10
        appDatabase.hospitalDao()
            .insertHospital(HospitalDataFactory.makeListOfRoomHospital(predefinedEntrySize))
        val hospitalEntrySize = appDatabase.hospitalDao().retrieveAllHospitals().size
        Assert.assertEquals(hospitalEntrySize, predefinedEntrySize)
    }

    @Test
    fun deleteHospitalEntryTest() {
        val predefinedEntrySize = 0
        val roomHospitalItem = HospitalDataFactory.makeSingleRoomHospitalEntry()
        appDatabase.hospitalDao()
            .insertHospital(mutableListOf(roomHospitalItem))
        appDatabase.hospitalDao().deleteHospitalEntry(roomHospitalItem)
        Assert.assertEquals(appDatabase.hospitalDao().retrieveAllHospitals().size, predefinedEntrySize)
    }
}
