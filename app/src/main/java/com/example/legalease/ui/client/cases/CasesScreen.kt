package com.example.legalease.ui.client.cases

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.Inter

@Composable
fun CasesScreen(modifier: Modifier = Modifier) {
    val casesScreenViewModel: CasesScreenViewModel = viewModel()
    LazyColumn(modifier = modifier) {
        items(casesScreenViewModel.casesItemList) {
            CaseItem(
                title = it.title,
                description = it.description,
                upcomingHearing = it.upcomingHearing,
                onItemClick = {},
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CaseItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    upcomingHearing: String,
    onItemClick: () -> Unit
) {

    Card(
        modifier = modifier.clickable { onItemClick() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontFamily = Inter,
                color = MaterialTheme.colorScheme.primaryContainer,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                textAlign = TextAlign.Justify,
                style = TextStyle(
                    letterSpacing = 0.18.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Upcoming Hearing: $upcomingHearing", color = Color.Blue)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CaseScreenPreview() {
    CasesScreen(

    )
}