package com.example.legalease.ui.client.caseDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.ui.components.RectangularChipsComponent
import com.example.legalease.ui.lawyer.recievedCasesFromClients.DocumentItem
import com.example.ui.theme.Inter


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
    expertise: List<String>
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = caseTitle,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            if (caseStatus == "Active")
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.RadioButtonChecked,
                        contentDescription = "Point",
                        tint = Color(52, 168, 83),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = caseStatus, color = Color(52, 168, 83), fontSize = 16.sp)
                }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Case Description",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = caseDescription)
        Spacer(modifier = Modifier.height(16.dp))
        RectangularChipsComponent(skills = expertise, tagShape = 1)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Appointments",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Upcoming",
                fontFamily = Inter,
                fontWeight = FontWeight(500),
                fontSize = 16.sp
            )
            Text(text = "12/06/24")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Lawyer",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(text = lawyerName, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = lawyerLevel)
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                AsyncImage(
                    model = null,
                    error = painterResource(id = R.drawable.default_profile_image),
                    contentDescription = "Lawyer Profile Image",
                    modifier = Modifier.size(70.dp).clip(CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Documents",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            DocumentItem(
                documentName = "Affidavit",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            DocumentItem(
                documentName = "Affidavit",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            DocumentItem(
                documentName = "Affidavit",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            DocumentItem(
                documentName = "Affidavit",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Case Timeline",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
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
fun CaseTimeLineItem(
    modifier: Modifier = Modifier,
    eventDescription: String,
    caseDate: String
) {
    Column(modifier = modifier) {
        Text(
            text = "Date:$caseDate",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
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
        caseDescription = "Lorem ipsum dolor sit amet, consectetuer adipiscing" +
                " elit. Aenean commodo ligula eget dolor. Aenean massa. " +
                "Cum sociis natoque penatibus et magnis dis parturient" +
                " montes, nascetur",
        modifier = Modifier.padding(16.dp),
        lawyerName = "Gautam Shorewala",
        lawyerLevel = "Sr. Advocate",
        caseNumber = "12345",
        caseStatus = "Active",
        clientName = "JohnDoe",
        expertise = listOf("Criminal", "Civil")
    )
}