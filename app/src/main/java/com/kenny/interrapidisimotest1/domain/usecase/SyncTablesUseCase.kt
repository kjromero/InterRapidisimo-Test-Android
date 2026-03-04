package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.repository.TableRepository
import javax.inject.Inject

/**
 * This Use Case syncs the tables from the API.
 */
class SyncTablesUseCase @Inject constructor(
    private val tablaRepository: TableRepository,
) {
    suspend operator fun invoke(): Either<DomainError, Unit> = tablaRepository.syncTables()
}