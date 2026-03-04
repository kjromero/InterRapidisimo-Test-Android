package com.kenny.interrapidisimotest1.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kenny.interrapidisimotest1.domain.model.User
import com.kenny.interrapidisimotest1.ui.common.ErrorMessage
import com.kenny.interrapidisimotest1.ui.common.LoadingOverlay
import com.kenny.interrapidisimotest1.ui.home.state.HomeUiState
import com.kenny.interrapidisimotest1.ui.home.state.HomeViewModel
import com.kenny.interrapidisimotest1.ui.theme.InterBackground
import com.kenny.interrapidisimotest1.ui.theme.InterBlue
import com.kenny.interrapidisimotest1.ui.theme.InterOnSurface
import com.kenny.interrapidisimotest1.ui.theme.InterRed
import com.kenny.interrapidisimotest1.ui.theme.InterYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToTables: () -> Unit,
    onNavigateToLocalities: () -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "INTERRAPIDÍSIMO",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = InterBlue,
                    titleContentColor = Color.White,
                ),
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(InterBackground),
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> LoadingOverlay()
                is HomeUiState.Ready -> HomeContent(
                    user = state.user,
                    onNavigateToTables = onNavigateToTables,
                    onNavigateToLocalities = onNavigateToLocalities,
                )
                is HomeUiState.Error -> ErrorMessage("No se encontraron datos del usuario.")
            }
        }
    }
}

@Composable
private fun HomeContent(
    user: User,
    onNavigateToTables: () -> Unit,
    onNavigateToLocalities: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Información del Usuario",
                    style = MaterialTheme.typography.titleMedium,
                    color = InterBlue,
                    fontWeight = FontWeight.SemiBold,
                )
                HorizontalDivider(color = InterYellow, thickness = 2.dp)
                UserInfoRow(label = "Usuario", value = user.username)
                UserInfoRow(label = "Identificación", value = user.identification)
                UserInfoRow(label = "Nombre", value = user.name)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNavigateToTables,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = InterBlue),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text("Tablas", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Button(
            onClick = onNavigateToLocalities,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = InterRed),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text("Localidades", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun UserInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = InterOnSurface,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = InterOnSurface,
        )
    }
}