package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.VersionStatus
import com.kenny.interrapidisimotest1.domain.repository.VersionRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    private val versionRepository: VersionRepository,
) {
    suspend operator fun invoke(localVersion: Int): VersionStatus {
        val remoteVersionStr = versionRepository.getRemoteVersion()
        val remoteVersion =  remoteVersionStr.toIntOrNull() ?: 0
        return when {
            localVersion < remoteVersion -> VersionStatus.LocalIsOutdated(localVersion.toString(), remoteVersion.toString())
            localVersion > remoteVersion -> VersionStatus.LocalIsNewer(localVersion.toString(), remoteVersion.toString())
            else -> VersionStatus.Match(localVersion.toString())
        }
    }
}