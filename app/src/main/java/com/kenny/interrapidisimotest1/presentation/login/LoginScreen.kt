package com.kenny.interrapidisimotest1.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kenny.interrapidisimotest1.domain.model.VersionStatus
import com.kenny.interrapidisimotest1.presentation.common.ErrorDialog
import com.kenny.interrapidisimotest1.presentation.common.LoadingOverlay
import com.kenny.interrapidisimotest1.presentation.common.UiState
import com.kenny.interrapidisimotest1.presentation.login.state.LoginViewModel
import com.kenny.interrapidisimotest1.presentation.theme.InterBackground
import com.kenny.interrapidisimotest1.presentation.theme.InterBlue
import com.kenny.interrapidisimotest1.presentation.theme.InterSurface
import com.kenny.interrapidisimotest1.presentation.theme.InterYellow

@Composable
fun LoginScreen(onNavigateToHome: () -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val isInitializing by viewModel.isInitializing.collectAsState()
    val versionStatus by viewModel.versionStatus.collectAsState()
    val showVersionDialog by viewModel.showVersionDialog.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigateToHome.collect { onNavigateToHome() }
    }

    if (showVersionDialog && versionStatus != null) {
        VersionMismatchDialog(
            status = versionStatus!!,
            onDismiss = viewModel::dismissVersionDialog,
        )
    }

    if (loginState is UiState.Error) {
        ErrorDialog(
            message = (loginState as UiState.Error).message,
            onDismiss = viewModel::dismissError,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InterBackground),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(InterBlue)
                .statusBarsPadding()
                .padding(vertical = 48.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = "INTERRAPIDÍSIMO",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                )
                Text(
                    text = "Controller APP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InterYellow,
                )
            }
        }

        if (isInitializing) {
            LoadingOverlay()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = viewModel::login,
                    enabled = loginState !is UiState.Loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = InterBlue),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    if (loginState is UiState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(
                            text = "Iniciar Sesión",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@Composable
private fun VersionMismatchDialog(status: VersionStatus, onDismiss: () -> Unit) {
    val (title, message) = when (status) {
        is VersionStatus.LocalIsOutdated ->
            "Versión desactualizada" to
                "La versión del servidor es ${status.remoteVersion} y tu aplicación tiene la versión ${status.localVersion}. Te recomendamos actualizar."
        is VersionStatus.LocalIsNewer ->
            "Versión no coincide" to
                "Tu aplicación (${status.localVersion}) es más reciente que el servidor (${status.remoteVersion})."
        is VersionStatus.Match -> return
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Entendido") }
        },
        containerColor = InterSurface,
    )
}