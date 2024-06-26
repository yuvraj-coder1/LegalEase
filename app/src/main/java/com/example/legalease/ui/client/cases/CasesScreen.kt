package com.example.legalease.ui.client.cases

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legalease.R
import com.example.ui.theme.Inter

@Composable
fun CasesScreen(modifier: Modifier = Modifier) {
    val casesScreenViewModel: CasesScreenViewModel = hiltViewModel()
    var currentFilter by rememberSaveable {
        mutableStateOf("All")
    }
    val casesItemList = casesScreenViewModel.casesItemList.collectAsState()
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { currentFilter = "All" }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = stringResource(R.string.all))
            }
            Button(onClick = { currentFilter = "active" },modifier = Modifier.padding(end = 8.dp)) {
                Text(text = stringResource(R.string.active))
            }
            Button(onClick = { currentFilter = "inactive" },modifier = Modifier.padding(end = 8.dp)) {
                Text(text = stringResource(R.string.inactive))
            }
            Button(onClick = { currentFilter = "pending" },modifier = Modifier.padding(end = 8.dp)) {
                Text(text = stringResource(R.string.pending))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(casesItemList.value.casesItemList.filter {
                if (currentFilter != "All") {
                    it.status == currentFilter
                } else {
                    it.caseType != "null"
                }
            }) {
                it.upcomingHearing?.let { it1 ->
                    CaseItem(
                        title = it.caseType,
                        description = it.description,
                        upcomingHearing = it1,
                        onItemClick = {},
                        modifier = modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        status = it.status
                    )
                }
            }
        }
    }
}

@Composable
fun CaseItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    upcomingHearing: String,
    onItemClick: () -> Unit,
    status: String
) {

    Card(
        modifier = modifier
            .clickable { onItemClick() }
            .shadow(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    letterSpacing = (-0.2).sp
                )
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (status) {
                            "active" -> Icons.Default.RadioButtonChecked
                            "inactive" -> Icons.Default.Update
                            "pending" -> Icons.Default.Update
                            else -> Icons.Default.RadioButtonChecked
                        },
                        contentDescription = "Point",
                        tint = when (status) {
                            "active" -> Color(52, 168, 83)
                            "inactive" -> Color.Red
                            "pending" -> Color(234, 187, 51)
                            else -> Color.Green
                        },
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = status,
                        color = when (status) {
                            "active" -> Color(52, 168, 83)
                            "inactive" -> Color.Red
                            "pending" -> Color(234, 187, 51)
                            else -> Color.Green
                        },
                        fontSize = 16.sp
                    )
                }

            }
            Spacer(modifier = Modifier.height(3.dp))
            HorizontalDivider(
                modifier = Modifier.width(80.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = description,
                fontFamily = Inter,
                color = MaterialTheme.colorScheme.primaryContainer,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = (-0.1).sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.upcoming_hearing),
                    fontFamily = Inter,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
                Spacer(modifier = Modifier.height(3.dp))
                HorizontalDivider(
                    modifier = Modifier.width(150.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    upcomingHearing,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontFamily = Inter,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CaseScreenPreview() {
    CasesScreen(

    )
}