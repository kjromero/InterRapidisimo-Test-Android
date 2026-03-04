package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.Tabla
import com.kenny.interrapidisimotest1.domain.repository.TablaRepository
import javax.inject.Inject

class GetTablesUseCase @Inject constructor(
    private val tablaRepository: TablaRepository,
) {
    suspend operator fun invoke(): List<Tabla> = tablaRepository.getTables()
}