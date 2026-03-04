package com.kenny.interrapidisimotest1.domain.repository

import com.kenny.interrapidisimotest1.domain.model.User

interface AuthRepository {
    suspend fun login(): User
    suspend fun getStoredUser(): User?
}