package com.example.legalease.ui.client.searchScreen

import android.media.Rating
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.legalease.ui.components.ChipsComponent
import com.example.legalease.ui.components.RectangularChipsComponent
//import com.example.legalease.ui.theme.
import com.example.compose.LegalEaseTheme
import com.example.legalease.ui.components.Tag

val tagList: List<String> = listOf("Personal", "Criminal", "Supreme Court", "Real estate", "Legal","Business")
val ratingList: List<String> = listOf("1.0+", "2.0+", "3.0+", "4.0+")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Practice Area",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChipsComponent(
            skills = tagList,
            isThisIndexSelected = uiState.isIndexSelected,
            selectedColor = Color(207, 118, 54),
            onChipClick = {viewModel.updateIsIndexSelected(it)}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Location",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.location,
            onValueChange = { viewModel.updateLocation(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Location") },
            enabled = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Red
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Experience",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Years")
        Column {
            if (uiState.experienceFilter.toInt() == 15)
                Text(text = "${uiState.experienceFilter.toInt().toString()}+")
            else
                Text(text = uiState.experienceFilter.toInt().toString())
            Slider(
                value = uiState.experienceFilter,
                onValueChange = { viewModel.updateExperience(it) },
                valueRange = 0f..15f,
                steps = 150,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Rating",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChipsComponent(
            skills = ratingList,
            isThisIndexSelected = uiState.isRatingSelected,
            selectedColor = Color(207, 118, 54),
            onChipClick = {viewModel.updateIsRatingSelected(it)},
            tagShape = 2,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(
                text = "Apply Filters",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun FilterScreenPreview(modifier: Modifier = Modifier) {
//    LegalEaseTheme {
//        FilterScreen()
//    }
//}