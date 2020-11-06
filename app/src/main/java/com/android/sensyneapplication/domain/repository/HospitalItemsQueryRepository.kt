package com.android.sensyneapplication.domain.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.android.sensyneapplication.domain.database.HospitalDao
import com.android.sensyneapplication.domain.mapper.HospitalResponseModelToRoomItemMapper
import com.android.sensyneapplication.domain.mapper.RoomResponseToHospitalItemMapper
import com.android.sensyneapplication.domain.model.RoomHospitalItem
import com.android.sensyneapplication.domain.remote.HospitalQueryService
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import javax.inject.Inject

open class HospitalItemsQueryRepository @Inject constructor(
    private val hospitalQueryService: HospitalQueryService,
    private val hospitalDao: HospitalDao,
    private val hospitalResponseModelToRoomItemMapper: HospitalResponseModelToRoomItemMapper,
    private val roomResponseModelToRoomItemMapper: RoomResponseToHospitalItemMapper
) :
    Repository {

    override suspend fun retrieveHospitals(): List<HospitalItem>? {
        hospitalDao.deleteAll()
        val deferredResponse = hospitalQueryService.retrieveAllHospitals().await()
        return if (deferredResponse.isSuccessful) {
            val listOfHospitals = deferredResponse.body()
            listOfHospitals?.let { populateDatabase(it as MutableList<HospitalItem>) }
            deferredResponse.body()
        } else {
            throw Exception()
        }
    }

    override suspend fun queryHospitals(query: SupportSQLiteQuery): List<HospitalItem> {
        return hospitalDao.retrieveAllHospitals(query)
            .map {
                roomResponseModelToRoomItemMapper.mapFromModel(
                    it
                )
            }
            .toList()
    }

    private fun populateDatabase(listOfHospitals: MutableList<HospitalItem>) {
        hospitalDao.insertHospital(
            listOfHospitals.map {
                hospitalResponseModelToRoomItemMapper.mapFromModel(
                    it
                )
            }
                .toList() as MutableList<RoomHospitalItem>
        )
    }
}
