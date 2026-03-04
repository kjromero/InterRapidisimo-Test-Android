package com.kenny.interrapidisimotest1.ui.login.state

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.VersionStatus

sealed class LoginUiState {
    object Initializing : LoginUiState()
    data class Ready(
        val versionStatus: VersionStatus? = null,
        val showVersionDialog: Boolean = false,
        val isLoginLoading: Boolean = false,
        val error: DomainError? = null,
    ) : LoginUiState()
}