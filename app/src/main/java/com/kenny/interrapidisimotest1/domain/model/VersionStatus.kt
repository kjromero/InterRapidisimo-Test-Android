package com.kenny.interrapidisimotest1.domain.model

/**
 * Represents the app version state.
 * When the local version is greater than the API version, the result is [LocalIsNewer].
 * When it is lower, the result is [LocalIsOutdated].
 * If both versions are equal, the result is [Match].
 */
sealed interface VersionStatus {
    data class Match(val version: String) : VersionStatus
    data class LocalIsOutdated(val localVersion: String, val remoteVersion: String) : VersionStatus
    data class LocalIsNewer(val localVersion: String, val remoteVersion: String) : VersionStatus
}