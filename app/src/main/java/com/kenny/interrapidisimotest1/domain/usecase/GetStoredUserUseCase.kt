package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.User
import com.kenny.interrapidisimotest1.domain.repository.AuthRepository
import javax.inject.Inject

class GetStoredUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): User? = authRepository.getStoredUser()
}