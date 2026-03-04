package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.remote.api.InterApi
import com.kenny.interrapidisimotest1.data.remote.mapper.HttpErrorMapper
import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Localidad
import com.kenny.interrapidisimotest1.domain.repository.LocalidadRepository
import java.io.IOException
import javax.inject.Inject

class LocalidadRepositoryImpl @Inject constructor(
    private val interApi: InterApi,
    private val errorMapper: HttpErrorMapper,
) : LocalidadRepository {

    override suspend fun getLocalities(): Either<DomainError, List<Localidad>> {
        return try {
            val response = interApi.getPickupLocations()
            if (response.isSuccessful) {
                val localities = response.body().orEmpty().mapNotNull { dto ->
                    dto.cityAbbreviation?.let { abbr ->
                        Localidad(cityAbbreviation = abbr, fullName = dto.fullName ?: "")
                    }
                }
                Either.Right(localities)
            } else {
                Either.Left(errorMapper.map(response.code()))
            }
        } catch (e: IOException) {
            Either.Left(DomainError.Network)
        } catch (e: Exception) {
            Either.Left(DomainError.Unknown)
        }
    }
}