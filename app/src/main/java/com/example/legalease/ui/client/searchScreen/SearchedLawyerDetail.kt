package com.example.legalease.ui.client.searchScreen

import android.content.ContentValues.TAG
import android.util.Log
import com.example.legalease.ui.lawyer.profile.LawyerProfileScreenViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.model.LawyerData
import com.example.legalease.ui.components.ChipsComponent
import com.example.legalease.ui.viewModels.AuthViewModel
import com.example.ui.theme.Inter


@Composable
fun SearchedLawyerDetailScreen(
    modifier: Modifier = Modifier,
    lawyerId: String = "",
    navigateToAddCaseScreen: (String) -> Unit = {},
    vm: AuthViewModel
) {
    Log.d(TAG, "SearchedLawyerDetailScreen: $lawyerId")
    LaunchedEffect(key1 = Unit) {
        vm.getLawyer(lawyerId)
    }
    val lawyerData = vm.currentLawyer ?: LawyerData()
    Log.d(TAG, "SearchedLawyerDetailScreen: ${lawyerData.expertise}")
    val lawyerProfileScreenViewModel: LawyerProfileScreenViewModel = hiltViewModel()
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                AsyncImage(
                    model = lawyerData.imageUrl,
                    error = painterResource(id = R.drawable.profile_image),
                    contentDescription = "lawyer profile photo",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = lawyerData.name,
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = lawyerData.lawyerType,
                    fontSize = 16.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Lawyer location")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = lawyerData.lawyerLocation,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = (-0.2).sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(id = R.string.fee_in_rupee)+" ${lawyerData.feesPerHour} "+stringResource(id = R.string.per_hour), color = Color.Gray)
            Row {
                for (i in 1..lawyerData.rating.toInt()) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Lawyer Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                }
                if (lawyerData.rating > lawyerData.rating.toInt()) {
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
                        tint = Color.Black
                    )
                }
                for (i in lawyerData.rating.toInt() + 2..5) {
                    Icon(
                        imageVector = Icons.Default.StarOutline,
                        contentDescription = "Lawyer Rating",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${lawyerData.rating} /5 ", color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navigateToAddCaseScreen(lawyerId) },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFD3D3D3))
        ) {
            Text(
                text = stringResource(R.string.send_case),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp),

                ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.about_me),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        fontFamily = Inter,
                        letterSpacing = (-0.1).sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = "edit profile Picture",
                        modifier = Modifier
                            .clickable { /*TODO*/ }
                            .padding(start = 8.dp)
                            .size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lawyerData.bio,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = (-0.2).sp
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),

            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                ) {
                Text(
                    text = stringResource(id = R.string.expertise),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = Inter,
                    letterSpacing = (-0.1).sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                ChipsComponent(skills = lawyerData.expertise, cardColor = Color(0xFFD3D3D3))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.client_reviews), fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                fontFamily = Inter,
                letterSpacing = (-0.1).sp,
                color = MaterialTheme.colorScheme.primary
            )
            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = stringResource(id = R.string.see_more),
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (lawyerProfileScreenViewModel.reviews.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_reviews_yet),
                fontSize = 18.sp,
                color = Color.DarkGray,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            lawyerProfileScreenViewModel.reviews.forEachIndexed { index, it ->
                com.example.legalease.ui.lawyer.profile.Review(
                    userName = it.userName,
                    date = it.date,
                    review = it.comment,
                    rating = it.rating,
                    userPhoto = it.userPhoto,
                    likes = it.likes,
                    dislikes = it.dislikes,
                    reviewNumber = index
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun Review(
    modifier: Modifier = Modifier,
    userName: String,
    date: String,
    review: String,
    rating: Double,
    userPhoto: Int,
    likes: Int,
    dislikes: Int,
    reviewNumber: Int,
    onLikeClick: () -> Unit = {},
    onDislikeClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = userPhoto),
                contentDescription = "User Photo",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            for (i in 1..rating.toInt()) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Filled Star")
            }
            for (i in rating.toInt() + 1..5) {
                Icon(imageVector = Icons.Outlined.StarBorder, contentDescription = "Outlined Star")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = review,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Icon(
                imageVector = Icons.Outlined.ThumbUp,
                contentDescription = "Like Comment",
                tint = Color.Gray,
                modifier = Modifier.clickable { onLikeClick() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = likes.toString(), color = Color.Gray)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Outlined.ThumbDown,
                contentDescription = "Dislike Comment",
                tint = Color.Gray,
                modifier = Modifier.clickable { onDislikeClick() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = dislikes.toString(), color = Color.Gray)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchedLawyerDetailScreenPreview(modifier: Modifier = Modifier) {
    SearchedLawyerDetailScreen(vm = hiltViewModel())
}

