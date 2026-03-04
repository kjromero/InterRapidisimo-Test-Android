package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.local.dao.TablaDao
import com.kenny.interrapidisimotest1.data.local.entity.TablaEntity
import com.kenny.interrapidisimotest1.data.remote.api.FrameworkApi
import com.kenny.interrapidisimotest1.domain.model.Tabla
import com.kenny.interrapidisimotest1.domain.repository.TablaRepository
import javax.inject.Inject

class TablaRepositoryImpl @Inject constructor(
    private val frameworkApi: FrameworkApi,
    private val tablaDao: TablaDao,
) : TablaRepository {

    override suspend fun syncTables() {
        val response = frameworkApi.getSchema()
        if (!response.isSuccessful) throw Exception("HTTP ${response.code()}: ${response.message()}")
        val entities = response.body().orEmpty().mapNotNull { dto ->
            dto.tableName?.let { name ->
                TablaEntity(
                    tableName = name,
                    description = dto.description,
                    keyFields = dto.keyFields,
                    active = dto.active,
                    serverName = dto.serverName,
                    databaseName = dto.databaseName,
                )
            }
        }
        tablaDao.deleteAll()
        tablaDao.upsertAll(entities)
    }

    override suspend fun getTables(): List<Tabla> =
        tablaDao.getAll().map { Tabla(it.tableName, it.description, it.keyFields, it.active) }
}