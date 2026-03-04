package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.User
import com.kenny.interrapidisimotest1.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): User = authRepository.login()
}