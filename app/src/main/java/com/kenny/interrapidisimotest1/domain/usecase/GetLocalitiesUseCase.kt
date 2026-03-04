package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Locality
import com.kenny.interrapidisimotest1.domain.repository.LocalityRepository
import javax.inject.Inject

class GetLocalitiesUseCase @Inject constructor(
    private val localityRepository: LocalityRepository,
) {
    suspend operator fun invoke(): Either<DomainError, List<Locality>> = localityRepository.getLocalities()
}