package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.User
import com.kenny.interrapidisimotest1.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * This Use Case returns the stored user.
 */
class GetStoredUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): User? = authRepository.getStoredUser()
}