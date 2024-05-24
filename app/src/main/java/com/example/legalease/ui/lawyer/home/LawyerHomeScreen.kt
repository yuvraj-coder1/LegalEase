package com.example.legalease.ui.lawyer.home

import android.annotation.SuppressLint
import android.icu.util.Calendar.WeekData
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.LegalEaseTheme
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LawyerHomeScreen(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = viewModel()
    val homeUiState by homeViewModel.uiState.collectAsState()
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (homeUiState.selectedDate == LocalDate.now()) "Today" else "${homeUiState.selectedDate.dayOfMonth} ${
                    homeUiState.selectedDate.month.toString().lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                }",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium
            )
            Row {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "back",
                    modifier = Modifier.clickable { homeViewModel.moveDatesLeft() }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Forward",
                    modifier = Modifier.clickable { homeViewModel.moveDatesRight() }
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            homeUiState.dateList.forEachIndexed { index, it ->
                Date(
                    date = it.date,
                    selected = it.selected || homeUiState.selectedDate == it.date,
                    onClick = { ind -> homeViewModel.selectDate(ind) },
                    modifier = Modifier.weight(1f),
                    index = index
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(5.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp)),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),


            ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "${homeUiState.selectedDate.dayOfWeek}, ${homeUiState.selectedDate.month} ${homeUiState.selectedDate.dayOfMonth}",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "You have a appointment with mr lawyer on 9:00am",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    letterSpacing = (0.1).sp
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Ongoing Cases",
//                style = MaterialTheme.typography.titleLarge
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "See All",
                modifier = Modifier.clickable { /*TODO*/ }
            )
//            Text(text = "See All", modifier = Modifier.clickable { /*TODO*/ })
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow {
            itemsIndexed(homeViewModel.casesItemList) { index, item ->
                OngoingCasesItem(
                    caseTitle = item.title,
                    caseDescription = item.description,
                    upcomingHearing = item.upcomingHearing,
                    lawyerName = "Gautam Shorewala",
                    lawyerProfile = item.profileImage,
//                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Others", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            maxItemsInEachRow = 4,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OtherIcon(icon = Icons.Default.QuestionMark, text = "FAQs", onClick = {})
            OtherIcon(icon = Icons.AutoMirrored.Filled.LibraryBooks, text = "Blogs", onClick = {})
            OtherIcon(icon = Icons.Default.AccessTime, text = "History", onClick = {})
            OtherIcon(icon = Icons.Default.MonetizationOn, text = "Financials", onClick = {})
        }
    }
}

@Composable
fun OtherIcon(modifier: Modifier = Modifier, icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier
            .clickable { /*TODO*/ }
            .clip(CircleShape)) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                modifier = Modifier.padding(10.dp)
            )
        }
        Text(text = text)
    }
}

@Composable
fun OngoingCasesItem(
    modifier: Modifier = Modifier,
    caseTitle: String,
    caseDescription: String,
    upcomingHearing: String,
    lawyerName: String,
    lawyerProfile: Int
) {
    Card(
        modifier = modifier.padding(end = 10.dp).width(350.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = lawyerProfile),
                    contentDescription = "lawyer profile photo",
                    modifier = Modifier.clip(CircleShape).size(50.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = lawyerName,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = caseTitle,
//                    style = MaterialTheme.typography.titleLarge
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = caseDescription,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    letterSpacing = (0.1).sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Upcoming Hearing ${upcomingHearing}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }


        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Date(
    modifier: Modifier = Modifier,
    date: LocalDate,
    selected: Boolean,
    onClick: (Int) -> Unit,
    index: Int
) {
    val weekDay = date.dayOfWeek.name.substring(0, 3).lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = weekDay,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Card(
            shape = CircleShape,
            modifier = Modifier.clickable { onClick(index) },
            colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LawyerHomeScreenPreview(modifier: Modifier = Modifier) {
    LegalEaseTheme {
        LawyerHomeScreen()
    }

}