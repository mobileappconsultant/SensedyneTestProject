package com.android.sensyneapplication.domain

interface ModelMapper<in M, out E> {
    fun mapFromModel(model: M): E
}
