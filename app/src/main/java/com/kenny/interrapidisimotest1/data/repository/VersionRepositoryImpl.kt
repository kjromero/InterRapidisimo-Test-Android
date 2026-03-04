package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.remote.api.InterApi
import com.kenny.interrapidisimotest1.data.remote.mapper.HttpErrorMapper
import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.repository.VersionRepository
import java.io.IOException
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val interApi: InterApi,
    private val errorMapper: HttpErrorMapper,
) : VersionRepository {

    override suspend fun getRemoteVersion(): Either<DomainError, String> {
        return try {
            val response = interApi.getVersionControl()
            if (response.isSuccessful) {
                val raw = response.body()?.string()?.trim()
                val value = raw?.removeSurrounding("\"") // Remove double quotes from the string
                if (!value.isNullOrBlank()) Either.Right(value) else Either.Left(DomainError.Unknown)
            } else {
                Either.Left(errorMapper.map(response.code()))
            }
        } catch (e: IOException) {
            println("Either exception Network: $e")
            Either.Left(DomainError.Network)
        } catch (e: Exception) {
            println("Either exception Unknown: $e")
            Either.Left(DomainError.Unknown)
        }
    }
}