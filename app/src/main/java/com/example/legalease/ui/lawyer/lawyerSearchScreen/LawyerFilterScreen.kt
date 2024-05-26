package com.example.legalease.ui.lawyer.lawyerSearchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun LawyerFilterScreen(
    modifier: Modifier = Modifier,
    viewModel: LawyerSearchScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Location",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.location,
            onValueChange = { viewModel.updateLocation(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Location") },
            enabled = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(
                text = "Apply Filters",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}