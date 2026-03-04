package com.kenny.interrapidisimotest1.presentation.localidades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.domain.model.Localidad
import com.kenny.interrapidisimotest1.domain.usecase.GetLocalidadesUseCase
import com.kenny.interrapidisimotest1.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalidadesViewModel @Inject constructor(
    private val getLocalidadesUseCase: GetLocalidadesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Localidad>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Localidad>>> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { getLocalidadesUseCase() }
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Failed to load localities") }
        }
    }
}