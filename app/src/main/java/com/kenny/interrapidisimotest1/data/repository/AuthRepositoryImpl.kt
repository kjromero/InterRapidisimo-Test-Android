package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.local.dao.UserDao
import com.kenny.interrapidisimotest1.data.local.entity.UserEntity
import com.kenny.interrapidisimotest1.data.remote.api.SecurityApi
import com.kenny.interrapidisimotest1.data.remote.dto.AuthRequestDto
import com.kenny.interrapidisimotest1.domain.model.User
import com.kenny.interrapidisimotest1.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val securityApi: SecurityApi,
    private val userDao: UserDao,
) : AuthRepository {

    override suspend fun login(): User {
        val response = securityApi.authenticate(AuthRequestDto())
        if (!response.isSuccessful) {
            throw Exception("HTTP ${response.code()}: ${response.message()}")
        }
        val dto = response.body() ?: throw Exception("Empty response from server")
        val user = User(
            username = dto.username ?: throw Exception("Invalid response: missing username"),
            identification = dto.identification ?: "",
            name = dto.name ?: "",
        )
        userDao.upsert(UserEntity(user.username, user.identification, user.name))
        return user
    }

    override suspend fun getStoredUser(): User? =
        userDao.getUser()?.let { User(it.username, it.identification, it.name) }
}