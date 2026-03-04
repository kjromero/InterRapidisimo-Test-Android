package com.kenny.interrapidisimotest1.domain.model

sealed interface VersionStatus {
    data class Match(val version: String) : VersionStatus
    data class LocalIsOutdated(val localVersion: String, val remoteVersion: String) : VersionStatus
    data class LocalIsNewer(val localVersion: String, val remoteVersion: String) : VersionStatus
}