package com.kenny.interrapidisimotest1.domain.repository

interface VersionRepository {
    suspend fun getRemoteVersion(): String
}