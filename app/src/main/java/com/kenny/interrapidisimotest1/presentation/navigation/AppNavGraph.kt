package com.kenny.interrapidisimotest1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kenny.interrapidisimotest1.presentation.home.HomeScreen
import com.kenny.interrapidisimotest1.presentation.localidades.LocalidadesScreen
import com.kenny.interrapidisimotest1.presentation.login.LoginScreen
import com.kenny.interrapidisimotest1.presentation.tablas.TablasScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToTables = { navController.navigate(Screen.Tables.route) },
                onNavigateToLocalities = { navController.navigate(Screen.Localities.route) },
            )
        }

        composable(Screen.Tables.route) {
            TablasScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Localities.route) {
            LocalidadesScreen(onBack = { navController.popBackStack() })
        }
    }
}