package com.kenny.interrapidisimotest1.domain.usecase

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.VersionStatus
import com.kenny.interrapidisimotest1.domain.model.VersionStatus.LocalIsNewer
import com.kenny.interrapidisimotest1.domain.model.VersionStatus.LocalIsOutdated
import com.kenny.interrapidisimotest1.domain.model.VersionStatus.Match
import com.kenny.interrapidisimotest1.domain.repository.VersionRepository
import javax.inject.Inject

/**
 * This Use Case return the app version state.
 * When the local version is greater than the API version, the result is [LocalIsNewer].
 * When it is lower, the result is [LocalIsOutdated].
 * If both versions are equal, the result is [Match].
 */
class CheckVersionUseCase @Inject constructor(
    private val versionRepository: VersionRepository,
) {
    suspend operator fun invoke(localVersion: Int): Either<DomainError, VersionStatus> {
        val result = versionRepository.getRemoteVersion()
        return when (result) {
            is Either.Left -> result
            is Either.Right -> {
                val remoteVersion = result.value.toIntOrNull() ?: 0
                Either.Right(
                    when {
                        localVersion < remoteVersion -> LocalIsOutdated(localVersion.toString(), remoteVersion.toString())
                        localVersion > remoteVersion -> LocalIsNewer(localVersion.toString(), remoteVersion.toString())
                        else -> Match(localVersion.toString())
                    }
                )
            }
        }
    }
}