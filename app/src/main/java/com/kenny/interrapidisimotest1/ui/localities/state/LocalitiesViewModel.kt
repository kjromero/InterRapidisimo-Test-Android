package com.kenny.interrapidisimotest1.ui.localities.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.usecase.GetLocalitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalitiesViewModel @Inject constructor(
    private val getLocalitiesUseCase: GetLocalitiesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LocalitiesUiState>(LocalitiesUiState.Loading)
    val uiState: StateFlow<LocalitiesUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = LocalitiesUiState.Loading
            when (val result = getLocalitiesUseCase()) {
                is Either.Left -> _uiState.value = LocalitiesUiState.Error(result.value)
                is Either.Right -> _uiState.value = LocalitiesUiState.Success(result.value)
            }
        }
    }
}