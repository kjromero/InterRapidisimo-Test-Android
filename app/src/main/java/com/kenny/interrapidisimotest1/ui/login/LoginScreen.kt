package com.kenny.interrapidisimotest1.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material3.Scaffold
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
import com.kenny.interrapidisimotest1.ui.common.ErrorDialog
import com.kenny.interrapidisimotest1.ui.common.LoadingOverlay
import com.kenny.interrapidisimotest1.ui.common.toUiMessage
import com.kenny.interrapidisimotest1.ui.login.state.LoginUiEvent
import com.kenny.interrapidisimotest1.ui.login.state.LoginUiState
import com.kenny.interrapidisimotest1.ui.login.state.LoginViewModel
import com.kenny.interrapidisimotest1.ui.theme.InterBackground
import com.kenny.interrapidisimotest1.ui.theme.InterBlue
import com.kenny.interrapidisimotest1.ui.theme.InterSurface
import com.kenny.interrapidisimotest1.ui.theme.InterYellow

@Composable
fun LoginScreen(onNavigateToHome: () -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginUiEvent.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    Scaffold(
        containerColor = InterBackground,
        contentWindowInsets = WindowInsets(0),
        topBar = {
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
                        text = "Test 1",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InterYellow,
                    )
                }
            }
        },
    ) { paddingValues ->
        when (val state = uiState) {
            is LoginUiState.Initializing -> LoadingOverlay(
                modifier = Modifier.padding(paddingValues),
            )
            is LoginUiState.Ready -> {
                if (state.showVersionDialog && state.versionStatus != null) {
                    VersionMismatchDialog(
                        status = state.versionStatus,
                        onDismiss = viewModel::dismissVersionDialog,
                    )
                }
                if (state.error != null) {
                    ErrorDialog(
                        message = state.error.toUiMessage(),
                        onDismiss = viewModel::dismissError,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = viewModel::login,
                        enabled = !state.isLoginLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = InterBlue),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        if (state.isLoginLoading) {
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
}

@Composable
private fun VersionMismatchDialog(status: VersionStatus, onDismiss: () -> Unit) {
    val (title, message) = when (status) {
        is VersionStatus.LocalIsOutdated ->
            "Versión local inferior a la  consultada en API" to
                "Versión del API:${status.remoteVersion}. Version local:${status.localVersion}."
        is VersionStatus.LocalIsNewer ->
            "Versión local superior a la consultada en el API" to
                "Version local: ${status.localVersion}. Versión del API: ${status.remoteVersion}."
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