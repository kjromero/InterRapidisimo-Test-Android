package com.kenny.interrapidisimotest1.presentation.login.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.BuildConfig
import com.kenny.interrapidisimotest1.domain.model.VersionStatus
import com.kenny.interrapidisimotest1.domain.usecase.CheckVersionUseCase
import com.kenny.interrapidisimotest1.domain.usecase.GetStoredUserUseCase
import com.kenny.interrapidisimotest1.domain.usecase.LoginUseCase
import com.kenny.interrapidisimotest1.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val loginUseCase: LoginUseCase,
    private val getStoredUserUseCase: GetStoredUserUseCase,
) : ViewModel() {

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing: StateFlow<Boolean> = _isInitializing.asStateFlow()

    private val _versionStatus = MutableStateFlow<VersionStatus?>(null)
    val versionStatus: StateFlow<VersionStatus?> = _versionStatus.asStateFlow()

    private val _showVersionDialog = MutableStateFlow(false)
    val showVersionDialog: StateFlow<Boolean> = _showVersionDialog.asStateFlow()

    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val loginState: StateFlow<UiState<Unit>> = _loginState.asStateFlow()

    private val _navigateToHome = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToHome: SharedFlow<Unit> = _navigateToHome.asSharedFlow()

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            runCatching { checkVersionUseCase(BuildConfig.VERSION_CODE) }
                .onSuccess { status ->
                    _versionStatus.value = status
                    if (status !is VersionStatus.Match) _showVersionDialog.value = true
                }

            val storedUser = runCatching { getStoredUserUseCase() }.getOrNull()
            _isInitializing.value = false

            if (storedUser != null) {
                _navigateToHome.tryEmit(Unit)
            }
        }
    }

    fun login() {
        if (_loginState.value is UiState.Loading) return
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            runCatching { loginUseCase() }
                .onSuccess {
                    _loginState.value = UiState.Success(Unit)
                    _navigateToHome.tryEmit(Unit)
                }
                .onFailure {
                    _loginState.value = UiState.Error(it.message ?: "Authentication failed")
                }
        }
    }

    fun dismissVersionDialog() {
        _showVersionDialog.value = false
    }

    fun dismissError() {
        _loginState.value = UiState.Idle
    }
}