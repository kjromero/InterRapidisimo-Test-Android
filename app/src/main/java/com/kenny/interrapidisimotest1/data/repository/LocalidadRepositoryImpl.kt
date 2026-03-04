package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.remote.api.FrameworkApi
import com.kenny.interrapidisimotest1.domain.model.Localidad
import com.kenny.interrapidisimotest1.domain.repository.LocalidadRepository
import javax.inject.Inject

class LocalidadRepositoryImpl @Inject constructor(
    private val frameworkApi: FrameworkApi,
) : LocalidadRepository {

    override suspend fun getLocalities(): List<Localidad> {
        val response = frameworkApi.getPickupLocations()
        if (!response.isSuccessful) throw Exception("HTTP ${response.code()}: ${response.message()}")
        return response.body().orEmpty().mapNotNull { dto ->
            dto.cityAbbreviation?.let { abbr ->
                Localidad(cityAbbreviation = abbr, fullName = dto.fullName ?: "")
            }
        }
    }
}