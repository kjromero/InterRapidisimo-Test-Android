package com.kenny.interrapidisimotest1.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.kenny.interrapidisimotest1.presentation.theme.InterRed
import com.kenny.interrapidisimotest1.presentation.theme.InterSurface

@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Error", color = InterRed) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Aceptar") }
        },
        containerColor = InterSurface,
    )
}