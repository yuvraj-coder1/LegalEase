package com.example.legalease.ui.lawyer.recievedCasesFromClients

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legalease.R
import com.example.compose.LegalEaseTheme

@Composable
fun ReceivedCasesFromClients(
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (String) -> Unit = {},
) {
    val viewModel: ReceivedCasesFromClientsViewModel =
        hiltViewModel<ReceivedCasesFromClientsViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getCasesFromClients()
    }
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(modifier = modifier) {
        itemsIndexed(uiState.cases) { index, case ->
            CaseItem(
                caseTitle = case.caseType,
                caseDescription = case.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navigateToDetailScreen(case.id)
                    },
                onLawyerDecision = {
                    viewModel.updateCaseStatus(
                        caseId = case.id,
                        status = it,
                        lawyerId = viewModel.getCurrentLawyerId()
                    )
                }
            )
        }
    }
}

@Composable
fun CaseItem(
    modifier: Modifier = Modifier,
    caseTitle: String,
    caseDescription: String,
    onLawyerDecision: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_profile_image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                Text(text = caseTitle, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = caseDescription, fontWeight = FontWeight.Normal)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                onClick = { onLawyerDecision("rejected") },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.buttonColors(Color.White),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black
                )
            ) {
                Text(text = "Decline", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onLawyerDecision("accepted") },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text(text = stringResource(id = R.string.accept))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ReceivedCasesFromClientsPreview() {
    LegalEaseTheme {
        ReceivedCasesFromClients()
    }
}