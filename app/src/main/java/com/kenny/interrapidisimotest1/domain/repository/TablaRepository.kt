package com.kenny.interrapidisimotest1.domain.repository

import com.kenny.interrapidisimotest1.domain.model.Tabla

interface TablaRepository {
    suspend fun syncTables()
    suspend fun getTables(): List<Tabla>
}