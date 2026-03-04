package com.kenny.interrapidisimotest1.ui.login.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.BuildConfig
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.VersionStatus
import com.kenny.interrapidisimotest1.domain.usecase.CheckVersionUseCase
import com.kenny.interrapidisimotest1.domain.usecase.GetStoredUserUseCase
import com.kenny.interrapidisimotest1.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val loginUseCase: LoginUseCase,
    private val getStoredUserUseCase: GetStoredUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initializing)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginUiEvent>(Channel.BUFFERED)
    val uiEvent: Flow<LoginUiEvent> = _uiEvent.receiveAsFlow()

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            val storedUser = runCatching { getStoredUserUseCase() }.getOrNull()

            if (storedUser != null) {
                _uiEvent.send(LoginUiEvent.NavigateToHome)
                return@launch
            }
            val versionResult = checkVersionUseCase(BuildConfig.VERSION_CODE)

            _uiState.value = when (versionResult) {
                is Either.Right -> {
                    val status = versionResult.value
                    LoginUiState.Ready(
                        versionStatus = status,
                        showVersionDialog = status !is VersionStatus.Match,
                    )
                }
                is Either.Left -> LoginUiState.Ready(
                    error = versionResult.value,
                )
            }
        }
    }

    fun login() {
        val current = _uiState.value as? LoginUiState.Ready ?: return
        if (current.isLoginLoading) return
        viewModelScope.launch {
            _uiState.value = current.copy(isLoginLoading = true, error = null)
            when (val result = loginUseCase()) {
                is Either.Right -> _uiEvent.send(LoginUiEvent.NavigateToHome)
                is Either.Left -> _uiState.value = current.copy(
                    isLoginLoading = false,
                    error = result.value,
                )
            }
        }
    }

    fun dismissVersionDialog() {
        val current = _uiState.value as? LoginUiState.Ready ?: return
        _uiState.value = current.copy(showVersionDialog = false)
    }

    fun dismissError() {
        val current = _uiState.value as? LoginUiState.Ready ?: return
        _uiState.value = current.copy(error = null)
    }
}