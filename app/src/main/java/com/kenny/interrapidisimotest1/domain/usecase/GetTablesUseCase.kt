package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.Table
import com.kenny.interrapidisimotest1.domain.repository.TableRepository
import javax.inject.Inject

class GetTablesUseCase @Inject constructor(
    private val tablaRepository: TableRepository,
) {
    suspend operator fun invoke(): List<Table> = tablaRepository.getTables()
}