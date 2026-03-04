package com.kenny.interrapidisimotest1.presentation.tablas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Table
import com.kenny.interrapidisimotest1.domain.usecase.GetTablesUseCase
import com.kenny.interrapidisimotest1.domain.usecase.SyncTablesUseCase
import com.kenny.interrapidisimotest1.presentation.common.UiState
import com.kenny.interrapidisimotest1.presentation.common.toUiMessage
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

    private val _uiState = MutableStateFlow<UiState<List<Table>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Table>>> = _uiState.asStateFlow()

    init {
        syncAndLoad()
    }

    private fun syncAndLoad() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = syncTablesUseCase()) {
                is Either.Left -> _uiState.value = UiState.Error(result.value.toUiMessage())
                is Either.Right -> _uiState.value = UiState.Success(getTablesUseCase())
            }
        }
    }
}