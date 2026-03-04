package com.kenny.interrapidisimotest1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = InterBlue,
    onPrimary = InterOnPrimary,
    secondary = InterRed,
    onSecondary = InterOnSecondary,
    tertiary = InterYellow,
    background = InterBackground,
    surface = InterSurface,
    onSurface = InterOnSurface,
)

@Composable
fun InterrapidisimoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}