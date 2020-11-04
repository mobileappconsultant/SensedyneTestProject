package com.android.sensyneapplication.framework.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HospitalQueryResponse(
    val hospitals: List<HospitalItem>?
)
