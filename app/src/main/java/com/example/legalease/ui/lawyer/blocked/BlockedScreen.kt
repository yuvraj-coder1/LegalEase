package com.example.legalease.ui.lawyer.blocked

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Inter

@Composable
fun BlockedLawyerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your verification has not been done yet :(",
            fontFamily = Inter,
            fontSize = 16.sp
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun BlockedLawyerScreenPreview() {
    BlockedLawyerScreen()
}