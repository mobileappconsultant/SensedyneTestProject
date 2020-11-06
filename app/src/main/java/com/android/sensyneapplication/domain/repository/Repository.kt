package com.android.sensyneapplication.domain.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.android.sensyneapplication.framework.domain.model.HospitalItem

interface Repository {
    suspend fun retrieveHospitals(): List<HospitalItem>?
    suspend fun queryHospitals(query: SupportSQLiteQuery): List<HospitalItem>
}
