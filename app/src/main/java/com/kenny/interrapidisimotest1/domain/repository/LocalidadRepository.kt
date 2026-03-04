package com.kenny.interrapidisimotest1.domain.repository

import com.kenny.interrapidisimotest1.domain.model.Localidad

interface LocalidadRepository {
    suspend fun getLocalities(): List<Localidad>
}