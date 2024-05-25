package com.example.legalease.ui.lawyer.profile


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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legalease.R
import com.example.legalease.ui.components.ChipsComponent


@Composable
fun LawyerProfileScreen(
    modifier: Modifier = Modifier,
    lawyerProfilePhoto: Int = R.drawable.profile_image,
    lawyerName: String = "Gautam Shorewala",
    lawyerProfession: String = "Corporate law",
    aboutLawyer: String = "Hey i am a lawyer",
    expertise: List<String> = listOf(
        "mergers",
        "Corporate Governance",
        "Securities Law",
        "Venture Capital",
        "Startups"
    ),
) {
    val lawyerProfileScreenViewModel: LawyerProfileScreenViewModel = viewModel()
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Box {
                Image(
                    painter = painterResource(id = lawyerProfilePhoto),
                    contentDescription = "lawyer profile photo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = "edit profile Picture",
                    modifier = Modifier
                        .align(
                            Alignment.TopEnd
                        )
                        .clickable { /*TODO*/ }
                        .padding(start = 8.dp))
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Log Out")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = lawyerName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = lawyerProfession,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "About Me",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
            Icon(imageVector = Icons.Default.Edit,
                contentDescription = "edit profile Picture",
                modifier = Modifier
                    .clickable { /*TODO*/ }
                    .padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = aboutLawyer,
            fontSize = 18.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Expertise",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipsComponent(skills = expertise, cardColor = Color(0xFFD3D3D3))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Client Reviews",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (lawyerProfileScreenViewModel.reviews.isEmpty()) {
            Text(
                text = "No reviews yet",
                fontSize = 18.sp,
                color = Color.DarkGray,
            )
        }
        Column {
            lawyerProfileScreenViewModel.reviews.forEachIndexed { index, it ->
                Review(
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
fun LawyerProfileScreenPreview(modifier: Modifier = Modifier) {
    LawyerProfileScreen()
}