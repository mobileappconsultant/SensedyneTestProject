package com.android.sensyneapplication

import com.android.sensyneapplication.domain.model.RoomHospitalItem
import com.android.sensyneapplication.framework.domain.model.HospitalItem

object HospitalDataFactory {

    fun randomUuid(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun makeSingleRoomHospitalEntry(): RoomHospitalItem {
        return RoomHospitalItem(
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid()
        )
    }

    fun makeSingleHospitalEntry(): HospitalItem {
        return HospitalItem(
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
        )
    }

    fun makeListOfRoomHospital(count: Int): MutableList<RoomHospitalItem> {
        val listOfHospitalRoomEntries = mutableListOf<RoomHospitalItem>()
        repeat(count) {
            listOfHospitalRoomEntries.add(makeSingleRoomHospitalEntry())
        }
        return listOfHospitalRoomEntries
    }
}
