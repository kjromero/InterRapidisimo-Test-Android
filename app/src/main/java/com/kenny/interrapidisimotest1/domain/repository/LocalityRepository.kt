package com.kenny.interrapidisimotest1.domain.repository

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Locality

interface LocalityRepository {
    suspend fun getLocalities(): Either<DomainError, List<Locality>>
}