package com.kenny.interrapidisimotest1.ui.tables.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.usecase.GetTablesUseCase
import com.kenny.interrapidisimotest1.domain.usecase.SyncTablesUseCase
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

    private val _uiState = MutableStateFlow<TablesUiState>(TablesUiState.Loading)
    val uiState: StateFlow<TablesUiState> = _uiState.asStateFlow()

    init {
        syncAndLoad()
    }

    private fun syncAndLoad() {
        viewModelScope.launch {
            _uiState.value = TablesUiState.Loading
            when (val result = syncTablesUseCase()) {
                is Either.Left -> _uiState.value = TablesUiState.Error(result.value)
                is Either.Right -> _uiState.value = TablesUiState.Success(getTablesUseCase())
            }
        }
    }
}