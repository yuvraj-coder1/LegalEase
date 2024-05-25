package com.example.legalease.ui.lawyer.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.rounded.ArrowBackIosNew
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.ui.signIn.SignInScreenViewModel
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LawyerHomeScreen(
    modifier: Modifier = Modifier,
    signedInViewModel: SignInScreenViewModel,
    onSeeAllClick: () -> Unit,
    navigateToAddAppointment: () -> Unit,
    onDocumentClick: () -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeUiState by homeViewModel.uiState.collectAsState()
    val uiState = signedInViewModel.uiState.collectAsState()
    val isUserLawyer = uiState.value.isLawyer

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (homeUiState.selectedDate == LocalDate.now()) stringResource(R.string.today) else "${homeUiState.selectedDate.dayOfMonth} ${
                    homeUiState.selectedDate.month.toString().lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                }",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium
            )
            Row {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "back",
                    modifier = Modifier.clickable { homeViewModel.moveDatesLeft() },
                    tint = MaterialTheme.colorScheme.tertiaryContainer
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Forward",
                    modifier = Modifier.clickable { homeViewModel.moveDatesRight() },
                    tint = MaterialTheme.colorScheme.tertiaryContainer
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
        if (homeUiState.appointments.isNotEmpty()) {
            homeUiState.appointments.filter {
                Log.d(
                    TAG,
                    "LawyerHomeScreen: ${it.date} && ${homeUiState.selectedDate}"
                )
                it.date == homeUiState.selectedDate.toString()
            }
                .forEach { appointment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
//                .padding(5.dp)
                            .shadow(10.dp, RoundedCornerShape(10.dp)),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                        border = BorderStroke(
                            1.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "${homeUiState.selectedDate.dayOfWeek}, ${homeUiState.selectedDate.month} ${homeUiState.selectedDate.dayOfMonth}",
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "You have a appointment with mr lawyer on ${appointment.time}",
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                letterSpacing = (0.1).sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
        }

        if (isUserLawyer) {
            Button(
                onClick = navigateToAddAppointment,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.book_appointment), modifier = Modifier.padding(10.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.9f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.cases),
//                style = MaterialTheme.typography.titleLarge
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.tertiaryContainer
            )
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "See All",
                modifier = Modifier.clickable { onSeeAllClick() },
                tint = MaterialTheme.colorScheme.tertiaryContainer
            )
//            Text(text = "See All", modifier = Modifier.clickable { /*TODO*/ })
        }
        Spacer(modifier = Modifier.height(15.dp))
//        Card(
//            modifier = modifier
//                .padding(start = 5.dp, end = 10.dp)
//                .width(360.dp)
////            .fillMaxWidth(0.9f)
//                .shadow(4.dp, RoundedCornerShape(10.dp)),
//
//            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
//        ) {
//
//        }
        if (homeUiState.cases.isEmpty()) {
            Text(
                text = stringResource(R.string.no_cases_available),
                modifier = Modifier
                    .fillMaxSize(),
//                            .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        } else {
            LazyRow {
                itemsIndexed(homeUiState.cases) { index, item ->
                    OngoingCasesItem(
                        caseTitle = item.caseType,
                        caseDescription = item.description,
                        upcomingHearing = item.upcomingHearing ?: "",
                        lawyerName = "",
                        lawyerProfile = 0,
//                    modifier = Modifier.padding(10.dp)
                    )
                }
            }


        }
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.9f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = stringResource(R.string.others),
//            style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.tertiaryContainer
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        FlowRow(
            modifier
                .fillMaxWidth(),
//                .padding(horizontal = 16.dp),
            maxItemsInEachRow = 4,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            OtherIcon(
                icon = Icons.Default.QuestionMark,
                text = stringResource(R.string.faqs),
                onClick = {},
            )
            OtherIcon(
                icon = Icons.AutoMirrored.Filled.LibraryBooks,
                text = stringResource(R.string.blogs),
                onClick = {})
            OtherIcon(
                icon = Icons.Default.Description,
                text = stringResource(R.string.documents),
                onClick = onDocumentClick
            )
            OtherIcon(
                icon = Icons.Default.AccessTime,
                text = stringResource(R.string.history),
                onClick = {})
        }
    }
}


@Composable
fun OtherIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier
            .clickable { onClick() }
            .clip(CircleShape),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                modifier = Modifier.padding(10.dp),
                tint = MaterialTheme.colorScheme.background
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
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
        modifier = modifier
            .padding(start = 5.dp, end = 10.dp)
            .width(360.dp)
//            .fillMaxWidth(0.9f)
            .shadow(4.dp, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = lawyerProfile,
                    error = painterResource(id = R.drawable.default_profile_image),
                    contentDescription = "lawyer profile photo",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = lawyerName,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = caseTitle,
//                    style = MaterialTheme.typography.titleLarge
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.tertiaryContainer
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
                    text = stringResource(R.string.upcoming_hearing) + "${upcomingHearing}",
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

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun LawyerHomeScreenPreview(modifier: Modifier = Modifier) {
//    LegalEaseTheme {
//        LawyerHomeScreen()
//    }
//
//}