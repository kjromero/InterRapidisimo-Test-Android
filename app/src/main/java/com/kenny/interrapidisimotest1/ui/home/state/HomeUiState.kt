package com.kenny.interrapidisimotest1.ui.home.state

import com.kenny.interrapidisimotest1.domain.model.User

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Ready(val user: User) : HomeUiState()
    object Error : HomeUiState()
}