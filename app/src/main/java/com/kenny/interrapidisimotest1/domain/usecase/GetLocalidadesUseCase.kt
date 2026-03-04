package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Localidad
import com.kenny.interrapidisimotest1.domain.repository.LocalidadRepository
import javax.inject.Inject

class GetLocalidadesUseCase @Inject constructor(
    private val localidadRepository: LocalidadRepository,
) {
    suspend operator fun invoke(): Either<DomainError, List<Localidad>> = localidadRepository.getLocalities()
}