package com.kenny.interrapidisimotest1.domain.repository

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Table

interface TableRepository {
    suspend fun syncTables(): Either<DomainError, Unit>
    suspend fun getTables(): List<Table>
}