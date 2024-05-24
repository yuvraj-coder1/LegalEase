package com.example.legalease.ui.client.searchScreen

data class SearchScreenUiState(
    val searchQuery: String = "",
    val searchResults: List<String> = emptyList(),
    val active: Boolean = false,
)
