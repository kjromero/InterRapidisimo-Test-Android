package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.local.dao.UserDao
import com.kenny.interrapidisimotest1.data.local.entity.UserEntity
import com.kenny.interrapidisimotest1.data.remote.api.InterApi
import com.kenny.interrapidisimotest1.data.remote.dto.AuthRequestDto
import com.kenny.interrapidisimotest1.data.remote.mapper.HttpErrorMapper
import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.User
import com.kenny.interrapidisimotest1.domain.repository.AuthRepository
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val interApi: InterApi,
    private val userDao: UserDao,
    private val errorMapper: HttpErrorMapper,
) : AuthRepository {

    override suspend fun login(): Either<DomainError, User> {
        return try {
            val response = interApi.authenticate(AuthRequestDto())
            if (response.isSuccessful) {
                val dto = response.body() ?: return Either.Left(DomainError.Unknown)
                val username = dto.username ?: return Either.Left(DomainError.Unknown)
                val user = User(
                    username = username,
                    identification = dto.identification ?: "",
                    name = dto.name ?: "",
                )
                userDao.upsert(UserEntity(user.username, user.identification, user.name))
                Either.Right(user)
            } else {
                Either.Left(errorMapper.map(response.code()))
            }
        } catch (e: IOException) {
            Either.Left(DomainError.Network)
        } catch (e: Exception) {
            Either.Left(DomainError.Unknown)
        }
    }

    override suspend fun getStoredUser(): User? =
        userDao.getUser()?.let { User(it.username, it.identification, it.name) }
}