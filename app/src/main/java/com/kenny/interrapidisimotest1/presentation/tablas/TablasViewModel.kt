package com.kenny.interrapidisimotest1.presentation.tablas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.domain.model.Tabla
import com.kenny.interrapidisimotest1.domain.usecase.GetTablesUseCase
import com.kenny.interrapidisimotest1.domain.usecase.SyncTablesUseCase
import com.kenny.interrapidisimotest1.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TablasViewModel @Inject constructor(
    private val syncTablesUseCase: SyncTablesUseCase,
    private val getTablesUseCase: GetTablesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Tabla>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Tabla>>> = _uiState.asStateFlow()

    init {
        syncAndLoad()
    }

    private fun syncAndLoad() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching {
                syncTablesUseCase()
                getTablesUseCase()
            }
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Failed to load tables") }
        }
    }
}