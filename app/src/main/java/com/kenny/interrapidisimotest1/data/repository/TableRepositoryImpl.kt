package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.local.dao.TableDao
import com.kenny.interrapidisimotest1.data.local.entity.TableEntity
import com.kenny.interrapidisimotest1.data.remote.api.InterApi
import com.kenny.interrapidisimotest1.data.remote.mapper.HttpErrorMapper
import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Table
import com.kenny.interrapidisimotest1.domain.repository.TableRepository
import java.io.IOException
import javax.inject.Inject

class TableRepositoryImpl @Inject constructor(
    private val interApi: InterApi,
    private val tablaDao: TableDao,
    private val errorMapper: HttpErrorMapper,
) : TableRepository {

    override suspend fun syncTables(): Either<DomainError, Unit> {
        return try {
            val response = interApi.getSchema()
            if (response.isSuccessful) {
                val entities = response.body().orEmpty().mapNotNull { dto ->
                    dto.tableName?.let { name ->
                        TableEntity(
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

    override suspend fun getTables(): List<Table> =
        tablaDao.getAll().map { Table(it.tableName, it.primaryKey, it.fieldCount, it.lastSyncDate) }
}