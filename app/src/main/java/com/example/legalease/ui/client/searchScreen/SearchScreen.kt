package com.example.legalease.ui.client.searchScreen

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.collection.emptyLongSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.StarHalf
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.model.LawyerData
import com.example.legalease.ui.navigation.SearchScreen
//import com.example.legalease.ui.theme.LegalEaseTheme
import com.example.compose.LegalEaseTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(modifier: Modifier = Modifier, onLawyerClicked: (LawyerData) -> Unit = {}) {
    val searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
    val searchScreenUiState by searchScreenViewModel.uiState.collectAsState()
    val lawyerList by searchScreenViewModel.lawyerList.collectAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchScreenUiState.searchQuery,
            onQueryChange = searchScreenViewModel::updateSearchQuery,
            onSearch = {
                searchScreenViewModel.getLawyerDetailsFromSearch(it)
                searchScreenViewModel.updateActive(false)
            },
            active = searchScreenUiState.active,
            onActiveChange = { searchScreenViewModel.updateActive(it) },
            placeholder = { Text(text = "Search ", modifier = Modifier.padding(start = 16.dp)) },
            trailingIcon = {
                if (searchScreenUiState.active && searchScreenUiState.searchQuery.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            searchScreenViewModel.updateSearchQuery("")
                        })
                } else if (searchScreenUiState.active) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable { searchScreenViewModel.updateActive(false) })
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        ) {}
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = { /*TODO*/ }, elevation = ButtonDefaults.buttonElevation(5.dp)) {
                Icon(imageVector = Icons.Default.FilterAlt, contentDescription = "Filter")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Filter")
            }
            Button(
                onClick = { /*TODO*/ },
                elevation = ButtonDefaults.buttonElevation(5.dp),
            ) {
                Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Sort")
            }
        }
        if (lawyerList.isNotEmpty()) {
            LazyColumn {
                items(lawyerList) {
                    LawyerItemFromList(
                        lawyerName = it.name,
                        lawyerType = it.lawyerType,
                        fees = it.feesPerHour,
                        lawyerRating = it.rating,
                        ratingCount = it.ratingCount,
                        lawyerImage = it.imageUrl,
                        lawyerTags = it.expertise,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable { onLawyerClicked(it) }
                    )
                }
            }
        } else {
            Text(
                text = "No Lawyer Found",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }

    }
}


@Composable
fun LawyerItemFromList(
    modifier: Modifier = Modifier,
    lawyerName: String,
    lawyerType: String,
    fees: String,
    lawyerRating: Double,
    ratingCount: Int,
    lawyerImage: String,
    lawyerTags: List<String>
) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(5.dp)) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = lawyerName, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = lawyerType, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Fee: ₹$fees/hr", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                if (lawyerTags.isNotEmpty())
                    Row(modifier = Modifier) {
                        Card(colors = CardDefaults.cardColors(Color(207, 118, 54))) {
                            Text(
                                text = lawyerTags[0],
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Card(colors = CardDefaults.cardColors(Color(207, 118, 54))) {
                            Text(
                                text = lawyerTags[1],
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = AbsoluteAlignment.Right) {
                AsyncImage(
                    model = lawyerImage,
                    error = painterResource(id = R.drawable.default_profile_image),
                    contentDescription = "Lawyer Image",
                    modifier = Modifier
                        .width(70.dp)
                        .clip(CircleShape)

                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    for (i in 1..lawyerRating.toInt()) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Lawyer Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    if (lawyerRating > lawyerRating.toInt()) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.StarHalf,
                            contentDescription = "Lawyer Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.StarOutline,
                            contentDescription = "Lawyer Rating",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Yellow
                        )
                    }
                    for (i in lawyerRating.toInt() + 2..5) {
                        Icon(
                            imageVector = Icons.Default.StarOutline,
                            contentDescription = "Lawyer Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$lawyerRating ($ratingCount ratings)",
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview(modifier: Modifier = Modifier) {
    LegalEaseTheme {
        com.example.legalease.ui.client.searchScreen.SearchScreen()
    }
}

@SuppressLint("SuspiciousModifierThen")
@Composable
fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier = this then
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }