package com.example.legalease.ui.client.searchScreen

data class SearchScreenUiState(
    val searchQuery: String = "",
    val searchResults: List<String> = emptyList(),
    val active: Boolean = false,
    val location:String = "",
    val ratingFilter:String = "0",
    val priceFilter:String = "",
    val experienceFilter:Float = 0f,
    val expertiseFilter:List<String> = emptyList(),
    val isIndexSelected:List<Boolean> = emptyList(),
    val isRatingSelected:List<Boolean> = listOf(false,false,false,false),
)
