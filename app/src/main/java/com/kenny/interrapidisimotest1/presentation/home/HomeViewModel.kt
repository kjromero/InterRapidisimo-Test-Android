package com.kenny.interrapidisimotest1.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.domain.model.User
import com.kenny.interrapidisimotest1.domain.usecase.GetStoredUserUseCase
import com.kenny.interrapidisimotest1.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoredUserUseCase: GetStoredUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            runCatching { getStoredUserUseCase() }
                .onSuccess { user ->
                    _uiState.value = if (user != null) UiState.Success(user)
                    else UiState.Error("No user data found")
                }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Unknown error") }
        }
    }
}