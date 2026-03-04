package com.kenny.interrapidisimotest1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kenny.interrapidisimotest1.presentation.navigation.AppNavGraph
import com.kenny.interrapidisimotest1.presentation.theme.InterrapidisimoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterrapidisimoTheme {
                AppNavGraph()
            }
        }
    }
}