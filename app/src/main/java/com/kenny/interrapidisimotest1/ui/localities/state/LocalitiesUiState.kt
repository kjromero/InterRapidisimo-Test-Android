package com.kenny.interrapidisimotest1.ui.localities.state

import com.kenny.interrapidisimotest1.domain.model.DomainError
import com.kenny.interrapidisimotest1.domain.model.Locality

sealed class LocalitiesUiState {
    object Loading : LocalitiesUiState()
    data class Success(val localities: List<Locality>) : LocalitiesUiState()
    data class Error(val error: DomainError) : LocalitiesUiState()
}