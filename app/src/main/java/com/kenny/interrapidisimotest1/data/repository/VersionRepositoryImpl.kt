package com.kenny.interrapidisimotest1.data.repository

import com.kenny.interrapidisimotest1.data.remote.api.FrameworkApi
import com.kenny.interrapidisimotest1.domain.repository.VersionRepository
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val frameworkApi: FrameworkApi,
) : VersionRepository {
    override suspend fun getRemoteVersion(): String {
        val response = frameworkApi.getVersionControl()
        if (!response.isSuccessful) throw Exception("Version check failed: HTTP ${response.code()}")
        return response.body()?.value?.trim()
            ?: throw Exception("Remote version not available")
    }
}