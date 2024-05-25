package com.example.legalease.ui.client.caseDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.legalease.model.CaseData
import com.example.legalease.ui.lawyer.recievedCasesFromClients.DocumentItem

@Composable
fun DocumentListScreen(modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    val viewModel:DocumentListScreenViewModel = hiltViewModel()
    val cases = viewModel.caseListForDocument.collectAsState()
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(cases.value) {
            Text(text = it.caseType)
            Spacer(modifier = Modifier.height(16.dp))
            DocumentListForCase(documentList = it.documentLinks,onClick = {onClick(it)})
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DocumentListForCase(modifier: Modifier = Modifier, documentList: List<String> = emptyList(),onClick:(String)->Unit) {
    Column(modifier = modifier) {
        documentList.forEach {
            DocumentItem(
                documentName = it.substring(0, 10), modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = { onClick(it) }
            )
        }
    }
}