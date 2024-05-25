package com.example.legalease.ui.client.sendCaseToLawyer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.LegalEaseTheme
import com.example.legalease.model.CaseData
import com.example.ui.theme.Inter

@Composable
fun SendCaseToLawyerScreen(
    modifier: Modifier = Modifier,
    lawyerId: String = "",
    navigateUp: () -> Unit = {}
) {
    val viewModel: SendCaseToLawyerViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        itemsIndexed(uiState.caseList.filter { it.status == "inactive" }) { index, case ->
            CaseToSendToLawyerItem(
                caseType = case.caseType,
                caseDescription = case.description,
                caseDate = case.createdAt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onSendCase = {
                    viewModel.updateCase(caseId = case.id, lawyerId = lawyerId)
                    navigateUp()
                }
            )
        }
    }
}

@Composable
fun CaseToSendToLawyerItem(
    modifier: Modifier = Modifier,
    caseType: String,
    caseDescription: String,
    caseDate: String,
    onSendCase: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .padding(start = 16.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = caseType,
                    fontFamily = Inter,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = caseDate, color = Color(0, 0, 0, 64))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = caseDescription,
                fontFamily = Inter,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(51, 101, 198)
                    )
                ) {
                    Text(text = "View", color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onSendCase() },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = ButtonDefaults.buttonColors(Color(51, 101, 198))
                ) {
                    Text(text = "Send")
                }
            }
        }
    }
}


@Preview
@Composable
fun SendCaseToLawyerScreenPreview(modifier: Modifier = Modifier) {
    LegalEaseTheme {
        SendCaseToLawyerScreen()
    }
}