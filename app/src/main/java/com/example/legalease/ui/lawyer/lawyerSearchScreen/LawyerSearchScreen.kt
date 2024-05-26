package com.example.legalease.ui.lawyer.lawyerSearchScreen

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.model.CaseData
import com.example.legalease.model.LawyerData
import com.example.legalease.ui.client.searchScreen.noRippleClickable
import com.example.ui.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LawyerSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: LawyerSearchScreenViewModel = hiltViewModel(),
    onFilterClicked: () -> Unit = {},
    onCaseClicked: (CaseData) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val caseList by viewModel.caseList.collectAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = uiState.searchQuery,
            onQueryChange = viewModel::updateSearchQuery,
            onSearch = {
                viewModel.getCasesFromClients()
                viewModel.updateActive(false)
            },
            active = uiState.active,
            onActiveChange = { viewModel.updateActive(it) },
            placeholder = { Text(stringResource(id = R.string.search)) },
            leadingIcon = {
                if (uiState.active && uiState.searchQuery.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            viewModel.updateSearchQuery("")
                        })
                } else if (uiState.active) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable { viewModel.updateActive(false) })
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        ) {

        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = onFilterClicked, elevation = ButtonDefaults.buttonElevation(5.dp)) {
                Text(text = stringResource(id = R.string.filter))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.FilterAlt, contentDescription = "Filter")
            }
            Button(
                onClick = { /*TODO*/ },
                elevation = ButtonDefaults.buttonElevation(5.dp),
            ) {
                Text(text = stringResource(id = R.string.sort))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        if (caseList.isNotEmpty()) {
            LazyColumn {
                items(caseList.filter { if (it.location != "") uiState.location == it.location else true }) {
                    CaseItemFromList(
                        caseType = it.caseType,
                        clientName = it.clientName,
                        clientImage = null.toString(),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable { onCaseClicked(it) }
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(200.dp))
            Text(
                text = stringResource(R.string.oops_no_cases_found),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                letterSpacing = (-0.1).sp,
                fontSize = 16.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium
            )

        }

    }
}

@Composable
fun CaseItemFromList(
    modifier: Modifier = Modifier,
    caseType: String,
    clientName: String,
    clientImage: String,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = caseType,
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = clientName,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    letterSpacing = (-0.1).sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = AbsoluteAlignment.Right) {
                AsyncImage(
                    model = clientImage,
                    error = painterResource(id = R.drawable.default_profile_image),
                    contentDescription = "Lawyer Image",
                    modifier = Modifier
                        .width(50.dp)
                        .clip(CircleShape)

                )
            }
        }
    }
}