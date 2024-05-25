package com.example.legalease.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Tag(
    tagText: String,
    modifier: Modifier = Modifier,
    cardColor: Color,
    tagShape: Int = 100,
    textPadding: Dp = 0.dp,
    onChipClick: (Int) -> Unit = {},
    index: Int,
    isThisIndexSelected: Boolean = false,
    selectedColor: Color
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(tagShape.dp))
            .clickable { onChipClick(index) },
        colors = if (isThisIndexSelected) CardDefaults.cardColors(selectedColor) else CardDefaults.cardColors(
            containerColor = cardColor
        )
//            .border(
//                BorderStroke(1.dp, Color(0xFF018786)), shape = RoundedCornerShape(100.dp)
//            )
//            .padding(12.dp)
    ) {
        Text(
            text = tagText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .padding(textPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsComponent(
    skills: List<String>,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.primary,
    onChipClick: (Int) -> Unit = {},
    isThisIndexSelected: List<Boolean> = List(skills.size) { false },
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    tagShape: Int = 100,
) {
    if (skills.isNotEmpty())
        FlowRow(
        ) {
            skills.forEachIndexed() { index, item ->
                Tag(
                    item,
                    modifier = modifier,
                    cardColor = cardColor,
                    index = index,
                    isThisIndexSelected = isThisIndexSelected[index],
                    onChipClick = onChipClick,
                    selectedColor = selectedColor,
                    tagShape = tagShape
                )
            }
        }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RectangularChipsComponent(
    skills: List<String>,
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.primary,
    tagShape: Int = 100,
    textPadding: Dp = 0.dp
) {
    FlowRow(
    ) {
        skills.forEach() { item ->

            Tag(
                item,
                modifier = modifier,
                cardColor = cardColor,
                tagShape = tagShape,
                textPadding = textPadding,
                index = 0,
                isThisIndexSelected = false,
                selectedColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ChipsComponentPreview() {
    val skillsList =
        listOf("Web Development", "JavaScript", "UI/UX Design", "Machine Learning", "Data Science")

    ChipsComponent(skillsList)
}