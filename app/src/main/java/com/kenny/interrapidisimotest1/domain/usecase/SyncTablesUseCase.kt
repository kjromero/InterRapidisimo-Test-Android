package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.repository.TablaRepository
import javax.inject.Inject

class SyncTablesUseCase @Inject constructor(
    private val tablaRepository: TablaRepository,
) {
    suspend operator fun invoke(): Either<DomainError, Unit> = tablaRepository.syncTables()
}