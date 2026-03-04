package com.kenny.interrapidisimotest1.ui.localities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kenny.interrapidisimotest1.domain.model.Locality
import com.kenny.interrapidisimotest1.ui.common.ErrorMessage
import com.kenny.interrapidisimotest1.ui.common.LoadingOverlay
import com.kenny.interrapidisimotest1.ui.common.toUiMessage
import com.kenny.interrapidisimotest1.ui.localities.state.LocalitiesUiState
import com.kenny.interrapidisimotest1.ui.localities.state.LocalitiesViewModel
import com.kenny.interrapidisimotest1.ui.theme.InterBackground
import com.kenny.interrapidisimotest1.ui.theme.InterBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalidadesScreen(onBack: () -> Unit) {
    val viewModel: LocalitiesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Localidades") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = InterBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
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
                is LocalitiesUiState.Loading -> LoadingOverlay()
                is LocalitiesUiState.Success -> LocalidadesList(state.localities)
                is LocalitiesUiState.Error -> ErrorMessage(state.error.toUiMessage())
            }
        }
    }
}

@Composable
private fun LocalidadesList(localities: List<Locality>) {
    if (localities.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay localidades disponibles", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(localities) { localidad ->
            LocalidadItem(localidad)
        }
    }
}

@Composable
private fun LocalidadItem(localidad: Locality) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .background(InterBlue, RoundedCornerShape(4.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            ) {
                Text(
                    text = localidad.cityAbbreviation,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = localidad.fullName,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
