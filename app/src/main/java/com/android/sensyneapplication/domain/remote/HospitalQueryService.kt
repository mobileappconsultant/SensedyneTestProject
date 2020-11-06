package com.android.sensyneapplication.domain.remote

import com.android.sensyneapplication.framework.domain.model.HospitalItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface HospitalQueryService {

    @GET("/shtest.github.io/hospitals.json")

    fun retrieveAllHospitals(): Deferred<Response<List<HospitalItem>>>
}
