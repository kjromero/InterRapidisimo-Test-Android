package com.kenny.interrapidisimotest1.presentation.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Tables : Screen("tables")
    data object Localities : Screen("localities")
}