package com.kenny.interrapidisimotest1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kenny.interrapidisimotest1.ui.navigation.AppNavGraph
import com.kenny.interrapidisimotest1.ui.theme.InterrapidisimoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
        )
        setContent {
            InterrapidisimoTheme {
                AppNavGraph()
            }
        }
    }
}