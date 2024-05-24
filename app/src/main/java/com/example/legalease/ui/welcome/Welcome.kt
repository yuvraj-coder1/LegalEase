package com.example.legalease.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.legalease.R
import com.example.legalease.ui.navigation.SignInScreen


@Composable
fun WelcomeScreen(navigateToNextScreen:() -> Unit) {
    val images = listOf(
        R.drawable.lawyer_client_talk,
        R.drawable.select_lawyer,
    )

    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    val threshold = 50.dp
                    if (dragAmount.dp < -threshold && currentIndex < images.size - 1) {
                        currentIndex = (currentIndex + 1) % images.size
                    } else if (dragAmount.dp > threshold && currentIndex > 0) {
                        currentIndex = (currentIndex - 1) % images.size
                    } else {
                        // Do nothing eat 5 star
                    }
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = images[currentIndex]),
            contentDescription = null,
            modifier = Modifier
                .size(500.dp)
        )

        // Indicator dots
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            repeat(images.size) { index ->
                Dot(
                    isSelected = index == currentIndex,
                    modifier = Modifier.size(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (currentIndex == 0) {
                Text(
                    text = "Easily look for lawyers",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "Easily look for cases",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }



        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navigateToNextScreen()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(text = "Get Started")
        }
    }
}

@Composable
fun Dot(isSelected: Boolean, modifier: Modifier = Modifier) {
    val color = if (isSelected) Color.White else Color.Gray
    Box(
        modifier = modifier
            .size(16.dp)
            .border(
                width = 0.5.dp, // Adjust the width of the border as needed
                color = Color.Black, // Adjust the color of the border as needed
                shape = CircleShape
            )
            .background(color = color, shape = CircleShape)
            .graphicsLayer {
                alpha = if (isSelected) 1f else 0.5f
            }
    )
}

@Preview
@Composable
fun PreviewScreenSliderWithIndicators() {
    Surface(color = Color.LightGray) {
        val navController = rememberNavController()
        WelcomeScreen{navController.navigate(SignInScreen)}
    }
}
