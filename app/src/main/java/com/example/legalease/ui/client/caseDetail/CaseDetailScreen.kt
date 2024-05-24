package com.example.legalease.ui.client.caseDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CasesDetailScreen(
    modifier: Modifier = Modifier,
    caseTitle: String,
    caseDescription: String,
    lawyerName: String,
    lawyerLevel: String,
    caseNumber: String,
    caseStatus: String,
    clientName: String,
    isLawyerAssigned: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
            Text(
                text = "Case Summary",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(alignment = Alignment.Start)
            )
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Case Title: $caseTitle",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Case Number: #$caseNumber")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Client: $clientName")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Case Status: $caseStatus")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Lawyer: $lawyerName")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Description: $caseDescription")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Related Documents",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 16.dp)

                )
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = "view all"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Document 1", style = MaterialTheme.typography.bodyLarge)
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "view pdf"
                        )
                    }
                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Document 2", style = MaterialTheme.typography.bodyLarge)
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "view pdf"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Case Timeline",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(alignment = Alignment.Start)

            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CaseTimeLineItem(
                        eventDescription = "Event Description 1",
                        caseDate = "2023-09-01",
                        modifier = Modifier.padding(8.dp)
                    )
                    CaseTimeLineItem(
                        eventDescription = "Event Description 1",
                        caseDate = "2023-09-01",
                        modifier = Modifier.padding(8.dp)
                    )
                    CaseTimeLineItem(
                        eventDescription = "Event Description 1",
                        caseDate = "2023-09-01",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
}

@Composable
fun CaseTimeLineItem(modifier: Modifier = Modifier,eventDescription: String, caseDate: String) {
    Column(modifier = modifier) {
        Text(text = "Date:$caseDate",fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = eventDescription, style = MaterialTheme.typography.bodyLarge)

    }
}

//@Composable
//fun CaseTimelineItem(modifier: Modifier = Modifier, caseDescription: String, caseDate: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Icon(
//                imageVector = Icons.Filled.FiberManualRecord,
//                contentDescription = "point",
//                modifier = Modifier.size(16.dp)
//            )
//            VerticalDivider(modifier = Modifier.height(40.dp), color = Color.Black)
//        }
//        Text(
//            text = caseDescription,
//            style = MaterialTheme.typography.bodyLarge,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(horizontal = 8.dp)
//                .weight(1f)
//
//        )
//        Text(text = caseDate, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
//    }
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CasesDetailScreenPreview() {
    CasesDetailScreen(
        caseTitle = "Cases Title",
        caseDescription = "Cases Description",
        modifier = Modifier.padding(16.dp),
        lawyerName = "Gautam Shorewala",
        lawyerLevel = "Sr. Advocate",
        caseNumber = "12345",
        caseStatus = "In Progress",
        clientName = "JohnDoe"
    )
}