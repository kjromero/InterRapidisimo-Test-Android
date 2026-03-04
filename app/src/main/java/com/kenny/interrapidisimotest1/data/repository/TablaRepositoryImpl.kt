package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.local.dao.TablaDao
import com.kenny.interrapidisimotest1.data.local.entity.TablaEntity
import com.kenny.interrapidisimotest1.data.remote.api.FrameworkApi
import com.kenny.interrapidisimotest1.data.remote.mapper.HttpErrorMapper
import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Tabla
import com.kenny.interrapidisimotest1.domain.repository.TablaRepository
import java.io.IOException
import javax.inject.Inject

class TablaRepositoryImpl @Inject constructor(
    private val frameworkApi: FrameworkApi,
    private val tablaDao: TablaDao,
    private val errorMapper: HttpErrorMapper,
) : TablaRepository {

    override suspend fun syncTables(): Either<DomainError, Unit> {
        return try {
            val response = frameworkApi.getSchema()
            if (response.isSuccessful) {
                val entities = response.body().orEmpty().mapNotNull { dto ->
                    dto.tableName?.let { name ->
                        TablaEntity(
                            tableName = name,
                            primaryKey = dto.primaryKey,
                            fieldCount = dto.fieldCount,
                            lastSyncDate = dto.lastSyncDate,
                        )
                    }
                }
                tablaDao.deleteAll()
                tablaDao.upsertAll(entities)
                Either.Right(Unit)
            } else {
                Either.Left(errorMapper.map(response.code()))
            }
        } catch (e: IOException) {
            Either.Left(DomainError.Network)
        } catch (e: Exception) {
            Either.Left(DomainError.Unknown)
        }
    }

    override suspend fun getTables(): List<Tabla> =
        tablaDao.getAll().map { Tabla(it.tableName, it.primaryKey, it.fieldCount, it.lastSyncDate) }
}