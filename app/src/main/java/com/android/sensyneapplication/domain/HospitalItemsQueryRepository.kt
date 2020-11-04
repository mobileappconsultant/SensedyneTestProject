package com.android.sensyneapplication.framework.domain

import androidx.sqlite.db.SupportSQLiteQuery
import com.android.sensyneapplication.domain.HospitalDao
import com.android.sensyneapplication.domain.model.HospitalResponseModelToRoomItemMapper
import com.android.sensyneapplication.domain.model.RoomHospitalItem
import com.android.sensyneapplication.domain.model.RoomResponseToHospitalItemMapper
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
