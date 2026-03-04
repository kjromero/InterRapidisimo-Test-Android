package com.kenny.interrapidisimotest1.presentation.localidades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.interrapidisimotest1.domain.model.Either
import com.kenny.interrapidisimotest1.domain.model.Locality
import com.kenny.interrapidisimotest1.domain.usecase.GetLocalidadesUseCase
import com.kenny.interrapidisimotest1.presentation.common.UiState
import com.kenny.interrapidisimotest1.presentation.common.toUiMessage
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

    private val _uiState = MutableStateFlow<UiState<List<Locality>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Locality>>> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = getLocalidadesUseCase()) {
                is Either.Left -> _uiState.value = UiState.Error(result.value.toUiMessage())
                is Either.Right -> _uiState.value = UiState.Success(result.value)
            }
        }
    }
}