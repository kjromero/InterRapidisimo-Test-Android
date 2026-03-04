package com.kenny.interrapidisimotest1.ui.login.state

sealed class LoginUiEvent {
    data object NavigateToHome : LoginUiEvent()
}