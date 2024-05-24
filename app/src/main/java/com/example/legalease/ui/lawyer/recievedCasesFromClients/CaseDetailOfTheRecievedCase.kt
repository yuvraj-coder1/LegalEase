package com.example.legalease.ui.lawyer.recievedCasesFromClients

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legalease.R
import com.example.legalease.model.CaseDetailSentToLawyer
import com.example.compose.LegalEaseTheme

@Composable
fun CaseDetailOfTheReceivedCase(
    modifier: Modifier = Modifier,
    caseTitle: String,
    caseDescription: String,
    clientName: String,
    caseDate: String,
    language: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = caseTitle,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_profile_image),
                contentDescription = "Client Profile Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                Text(text = clientName, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Case Created",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight(500)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = caseDate,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                            fontWeight = FontWeight(500)
                        )
                    }
                    VerticalDivider(modifier = Modifier.height(50.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Language",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight(500)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = language,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight(500)
                            )
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Case Description",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = caseDescription,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))
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
        Row {
            Button(
                onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text(text = "Accept")
            }
        }
    }
}

@Composable
fun DocumentItem(modifier: Modifier = Modifier, documentName: String, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier.clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(3.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = documentName)
            Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = "View pdf")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CaseDetailOfTheReceivedCasePreview(modifier: Modifier = Modifier) {
    LegalEaseTheme {
        CaseDetailOfTheReceivedCase(
            caseTitle = "Divorce Case",
            caseDescription = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit." +
                    " Aenean commodo ligula eget dolor. Aenean massa. Cum sociis " +
                    "natoque penatibus et magnis dis parturient montes, nascetur ridiculus " +
                    "mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. " +
                    "Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec," +
                    " vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae," +
                    " justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus." +
                    " Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula," +
                    " porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in," +
                    " viverra quis, feugiat a,",
            clientName = "Pratham Singh",
            caseDate = "23/12/2024",
            language = "English"
        )
    }
}