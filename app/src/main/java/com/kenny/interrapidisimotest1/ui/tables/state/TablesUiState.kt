package com.kenny.interrapidisimotest1.ui.tables.state

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Table

sealed class TablesUiState {
    object Loading : TablesUiState()
    data class Success(val tables: List<Table>) : TablesUiState()
    data class Error(val error: DomainError) : TablesUiState()
}